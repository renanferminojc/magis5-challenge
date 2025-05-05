package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import magis5.magis5challenge.response.DrinkSectionResponse;

public interface DrinkService {
  DrinkGetResponse findById(final String id);

  Drink findEntityById(String id);

  List<DrinkGetResponse> findAll();

  DrinkPostResponse save(final DrinkPostRequest drink);

  DrinkSectionResponse findByIdWithSections(final String drinkId);
}
