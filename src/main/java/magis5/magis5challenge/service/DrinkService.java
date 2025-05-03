package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.domain.Drink;

public interface DrinkService {
  List<Drink> findAll();

  Drink save(final Drink drink);

  Drink findById(final String id);
}
