package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkPostResponse;

public interface DrinkService {
  List<Drink> findAll();

  DrinkPostResponse save(final DrinkPostRequest drink);

  Drink findById(final String id);
}
