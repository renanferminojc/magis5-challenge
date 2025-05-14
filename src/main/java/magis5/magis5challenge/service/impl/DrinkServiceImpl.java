package magis5.magis5challenge.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.commons.PageRequestParams;
import magis5.magis5challenge.commons.PageableHelper;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.mapper.DrinkMapper;
import magis5.magis5challenge.mapper.SectionMapper;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.repository.DrinkSectionRepository;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import magis5.magis5challenge.response.DrinkWithSectionsResponse;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.service.DrinkService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  private final DrinkRepository drinkRepository;
  private final DrinkMapper drinkMapper;
  private final SectionMapper sectionMapper;
  private final DrinkSectionRepository drinkSectionRepository;

  public DrinkGetResponse findById(String id) {
    return findAndMap(UUID.fromString(id), drinkMapper::toDrinkGetResponse);
  }

  public DrinkWithSectionsResponse findDrinkWithSections(String drinkId) {
    List<DrinkSection> byDrinkId = drinkSectionRepository.findByDrinkId(UUID.fromString(drinkId));
    List<SectionGetResponse> list =
        byDrinkId.stream()
            .map(DrinkSection::getSection)
            .map(sectionMapper::toSectionGetResponse)
            .toList();
    DrinkWithSectionsResponse drinkWithSectionsResponse =
        drinkMapper.toDrinkWithSectionsResponse(byDrinkId.getFirst().getDrink());
    drinkWithSectionsResponse.setSections(list);
    return drinkWithSectionsResponse;
  }

  public Drink findEntityById(String id) {
    return findAndMap(UUID.fromString(id), Function.identity());
  }

  public List<DrinkGetResponse> findAll(PageRequestParams requestParams) {
    Pageable pageable = PageableHelper.createPageable(requestParams);
    List<Drink> drinkList = drinkRepository.findAll(pageable).getContent();
    return drinkMapper.toDrinkGetResponse(drinkList);
  }

  public DrinkPostResponse save(DrinkPostRequest request) {
    Drink drink = drinkMapper.toDrink(request);
    Drink drinkSaved = drinkRepository.save(drink);
    return drinkMapper.toDrinkPostResponse(drinkSaved);
  }

  private <R> R findAndMap(UUID id, Function<Drink, R> mapper) {
    return drinkRepository
        .findById(id)
        .map(mapper)
        .orElseThrow(() -> new NotFoundException("Drink not found"));
  }
}
