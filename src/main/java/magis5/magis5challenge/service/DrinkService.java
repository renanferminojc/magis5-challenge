package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.commons.PageRequestParams;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import magis5.magis5challenge.response.DrinkWithSectionsResponse;

public interface DrinkService {
  DrinkGetResponse findById(final String id);

  Drink findEntityById(final String id);

  List<DrinkGetResponse> findAll(PageRequestParams requestParams);

  DrinkPostResponse save(final DrinkPostRequest drink);

  DrinkWithSectionsResponse findDrinkWithSections(final String drinkId);
}
