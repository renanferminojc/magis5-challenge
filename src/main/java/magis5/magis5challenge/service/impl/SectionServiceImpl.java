package magis5.magis5challenge.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import magis5.magis5challenge.enumeration.ETransaction;
import magis5.magis5challenge.exception.CapacityExceededException;
import magis5.magis5challenge.exception.DrinkTypeException;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.exception.WithDrawException;
import magis5.magis5challenge.mapper.DrinkSectionMapper;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

  private final SectionRepository sectionRepository;
  private final DrinkSectionRepository drinkSectionRepository;

  private final DrinkService drinkService;
  private final HistoryService historyService;

  private final SectionMapper sectionMapper;
  private final DrinkSectionMapper drinkSectionMapper;

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

  public List<SectionGetResponse> findAll() {
    List<Section> sectionList = sectionRepository.findAll();
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

    if (section.getDrinkType() != drink.getType()) {
      throw new DrinkTypeException(
          "Section drink type mismatch, section type: %s".formatted(section.getDrinkType()));
    }

    BigDecimal drinkVolume = drink.getVolumeToBeStored(requestBody.getQty());
    if (exceedsCapacity(section, drinkVolume)) {
      throw new CapacityExceededException("Capacity exceeded");
    }

    if (ETransaction.IN.equals(transaction)) {
      storeADrink(section, drink, drinkVolume);
    } else {
      withDrawADrink(section, drinkVolume, drink, requestBody.getQty());
    }

    section.setUpdatedAt(LocalDateTime.now());
    Section saved = sectionRepository.save(section);

    historyService.save(saved, drink, transaction, drinkVolume);
    SectionWithDrinksResponse sectionWithDrinksResponse =
        sectionMapper.toSectionDrinkResponse(section);
    sectionWithDrinksResponse.setDrinks(
        drinkSectionMapper.toDrinkSectionResponse(section.getDrinkSections()));
    return sectionWithDrinksResponse;
  }

  private void storeADrink(Section section, Drink drink, BigDecimal drinkVolume) {
    section.addStock(drink, drinkVolume);
    section.setDrinkType(drink.getType());
    section.setStock(section.getStock().add(drinkVolume));
  }

  private void withDrawADrink(
      Section section, BigDecimal drinkVolume, Drink drink, BigDecimal qty) {
    if (!section.getDrinks().contains(drink)) {
      throw new WithDrawException("Drink not found in this section");
    }
    DrinkSection drinkSection =
        section.getDrinkSections().stream()
            .filter(ds -> ds.getDrink().equals(drink))
            .findFirst()
            .get();

    if (drinkVolume.compareTo(drinkSection.getDrink().getVolume()) >= 1) {
      section.getDrinkSections().remove(drinkSection);
      section.setStock(section.getStock().subtract(drinkSection.getVolume()));
    } else {
      section.setStock(
          section.getStock().subtract(drinkSection.getDrink().getVolume().multiply(qty)));
      drinkSection.setVolume(qty);
    }

    drinkSectionRepository.save(drinkSection);
    sectionRepository.save(section);
  }

  private boolean exceedsCapacity(final Section section, final BigDecimal drinkVolume) {
    BigDecimal max = section.getDrinkType().getMaxVolume();
    return drinkVolume.compareTo(max) > 0;
  }
}
