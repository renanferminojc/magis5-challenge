package magis5.magis5challenge.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.mapper.SectionMapper;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.repository.SectionRepository;
import magis5.magis5challenge.request.PostRequestSectionHoldDrink;
import magis5.magis5challenge.response.SectionDrinkResponse;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import magis5.magis5challenge.service.SectionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

  private final SectionRepository sectionRepository;
  private final DrinkRepository drinkRepository;
  private final SectionMapper sectionMapper;

  public SectionGetResponse findById(String id) {
    Section section =
        sectionRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException("Section not found"));
    return sectionMapper.toSectionGetResponse(section);
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

  public SectionDrinkResponse holdDrink(
      final String sectionId, final PostRequestSectionHoldDrink requestBody) {
    Section section =
        sectionRepository
            .findById(UUID.fromString(sectionId))
            .orElseThrow(() -> new NotFoundException("Section not found"));
    Drink drink =
        drinkRepository
            .findById(UUID.fromString(requestBody.getDrinkId()))
            .orElseThrow(() -> new NotFoundException("Drink not found"));

    if (!section.getDrinks().contains(drink)) {
      section.getDrinks().add(drink);
    }

    if (!drink.getSections().contains(section)) {
      drink.getSections().add(section);
    }

    section.setDrinkType(drink.getType());
    section.setStock(requestBody.getQty().multiply(drink.getVolume()));
    section.setUpdatedAt(LocalDateTime.now());
    Section saved = sectionRepository.save(section);
    return sectionMapper.toSectionDrinkResponse(saved);
  }
}
