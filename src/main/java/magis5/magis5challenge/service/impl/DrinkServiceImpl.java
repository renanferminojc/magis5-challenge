package magis5.magis5challenge.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.mapper.DrinkMapper;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import magis5.magis5challenge.service.DrinkService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  private final DrinkRepository drinkRepository;
  private final DrinkMapper drinkMapper;

  public DrinkGetResponse findById(String id) {
    return findAndMap(UUID.fromString(id), drinkMapper::toDrinkGetResponse);
  }

  public Drink findEntityById(String id) {
    return findAndMap(UUID.fromString(id), Function.identity());
  }

  public List<DrinkGetResponse> findAll() {
    List<Drink> drinkList = drinkRepository.findAll();
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
