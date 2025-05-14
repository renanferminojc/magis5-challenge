package magis5.magis5challenge.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.commons.DateUtils;
import magis5.magis5challenge.commons.PageableHelper;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import magis5.magis5challenge.enumeration.ETransaction;
import magis5.magis5challenge.exception.CapacityExceededException;
import magis5.magis5challenge.exception.DrinkTypeException;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.exception.WithDrawException;
import magis5.magis5challenge.mapper.SectionMapper;
import magis5.magis5challenge.repository.DrinkSectionRepository;
import magis5.magis5challenge.repository.SectionRepository;
import magis5.magis5challenge.request.PostRequestSectionHoldDrink;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import magis5.magis5challenge.response.SectionWithDrinksResponse;
import magis5.magis5challenge.service.DrinkService;
import magis5.magis5challenge.service.HistoryService;
import magis5.magis5challenge.service.SectionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

  private final SectionRepository sectionRepository;
  private final DrinkSectionRepository drinkSectionRepository;

  private final DrinkService drinkService;
  private final HistoryService historyService;

  private final SectionMapper sectionMapper;

  public SectionGetResponse findById(final String sectionId) {
    Section section =
        sectionRepository
            .findById(UUID.fromString(sectionId))
            .orElseThrow(() -> new NotFoundException("Section not found"));
    return sectionMapper.toSectionGetResponse(section);
  }

  public SectionWithDrinksResponse findByIdWithDrinks(final String sectionId) {
    Section section =
        sectionRepository
            .findWithStocksAndDrinksById(UUID.fromString(sectionId))
            .orElseThrow(() -> new NotFoundException(sectionId));

    return sectionMapper.toSectionDrinkResponse(section);
  }

  public List<SectionGetResponse> findAll(int page, int size, String sortBy, String sortDirection) {
    Pageable pageable = PageableHelper.createPageable(page, size, sortBy, sortDirection);

    List<Section> sectionList = sectionRepository.findAll(pageable).getContent();
    return sectionMapper.toSectionGetResponse(sectionList);
  }

  public SectionPostResponse save() {
    Section section =
        Section.builder().stock(BigDecimal.ZERO).drinkType(EDrinkType.NON_ALCOHOLIC).build();
    Section sectionSaved = sectionRepository.save(section);
    return sectionMapper.toSectionPostResponse(sectionSaved);
  }

  @Transactional
  public SectionWithDrinksResponse manageSection(
      final String sectionId, final PostRequestSectionHoldDrink requestBody) {
    Section section =
        sectionRepository
            .findWithStocksAndDrinksById(UUID.fromString(sectionId))
            .orElseThrow(() -> new NotFoundException(sectionId));
    Drink drink = drinkService.findEntityById(requestBody.getDrinkId());
    ETransaction transaction = ETransaction.valueOf(requestBody.getTransactionType());

    validateDrinkType(section, drink);
    BigDecimal drinkVolume = calculateDrinkVolume(drink, requestBody.getQty());
    validateCapacity(section, drinkVolume);

    if (ETransaction.IN.equals(transaction)) {
      storeADrink(section, drink, drinkVolume);
    } else {
      withDrawADrink(section, drink, requestBody.getQty());
    }

    section.setUpdatedAt(LocalDateTime.now());
    Section saved = sectionRepository.save(section);

    historyService.save(saved, drink, transaction, drinkVolume);
    return sectionMapper.toSectionDrinkResponse(section);
  }

  private void validateCapacity(Section section, BigDecimal drinkVolume) {
    if (exceedsCapacity(section, drinkVolume)) {
      throw new CapacityExceededException("Capacity exceeded");
    }
  }

  private BigDecimal calculateDrinkVolume(Drink drink, BigDecimal qty) {
    return drink.getVolume().multiply(qty);
  }

  private static void validateDrinkType(Section section, Drink drink) {
    if (DateUtils.hasBeen24HoursSinceUpdate(section.getUpdatedAt())
        && section.getDrinkType() == EDrinkType.NON_ALCOHOLIC
        && section.getDrinks().isEmpty()) {
      return;
    }

    if (section.sectionTypeIsNotEqualDrinkType(drink.getType())) {
      throw new DrinkTypeException(
          "Section drink type mismatch, section type: %s".formatted(section.getDrinkType()));
    }
  }

  private void storeADrink(Section section, Drink drink, BigDecimal drinkVolume) {
    DrinkSection drinkSection = findOrCreateDrinkSection(section, drink);
    drinkSection.setVolume(drinkSection.getVolume().add(drinkVolume));
    section.setStock(section.getStock().add(drinkVolume));
    section.setDrinkType(drink.getType());
    section.setStock(section.getStock().add(drinkVolume));
  }

  private void withDrawADrink(Section section, Drink drink, BigDecimal qty) {
    if (!section.getDrinks().contains(drink)) {
      throw new WithDrawException("Drink not found in this section");
    }
    DrinkSection drinkSection = getDrinkSectionByDrink(section, drink);

    var drinkVolumeToWithdraw = drink.getVolume().multiply(qty);

    if (isWithdrawAllDrink(drinkVolumeToWithdraw, drinkSection.getDrink())) {
      removeAllDrinkFromSection(section, drinkSection);
    } else {
      partialWithdrawDrinkFromSection(section, drinkSection, qty);
    }
    saveUpdatedData(section, drinkSection);
  }

  private static DrinkSection getDrinkSectionByDrink(Section section, Drink drink) {
    return section.getDrinkSections().stream()
        .filter(ds -> ds.getDrink().equals(drink))
        .findFirst()
        .get();
  }

  private boolean exceedsCapacity(final Section section, final BigDecimal drinkVolume) {
    BigDecimal max = section.getDrinkType().getMaxVolume();
    return drinkVolume.compareTo(max) > 0;
  }

  private boolean isWithdrawAllDrink(BigDecimal drinkVolumeToWithdraw, Drink drink) {
    return drinkVolumeToWithdraw.compareTo(drink.getVolume()) >= 0;
  }

  private void removeAllDrinkFromSection(Section section, DrinkSection drinkSection) {
    section.getDrinkSections().remove(drinkSection);
    section.setStock(section.getStock().subtract(drinkSection.getVolume()));
  }

  private void partialWithdrawDrinkFromSection(
      Section section, DrinkSection drinkSection, BigDecimal qty) {
    BigDecimal withdrawnVolume = drinkSection.getDrink().getVolume().multiply(qty);
    section.setStock(section.getStock().subtract(withdrawnVolume));
    drinkSection.setVolume(qty);
  }

  private void saveUpdatedData(final Section section, final DrinkSection drinkSection) {
    drinkSectionRepository.save(drinkSection);
    sectionRepository.save(section);
  }

  private DrinkSection findOrCreateDrinkSection(Section section, Drink drink) {
    return section.getDrinkSections().stream()
        .filter(ds -> ds.getDrink().equals(drink))
        .findFirst()
        .orElseGet(() -> createNewDrinkSection(section, drink));
  }

  private DrinkSection createNewDrinkSection(Section section, Drink drink) {
    DrinkSection drinkSection =
        DrinkSection.builder().drink(drink).section(section).volume(BigDecimal.ZERO).build();
    section.getDrinkSections().add(drinkSection);
    return drinkSection;
  }
}
