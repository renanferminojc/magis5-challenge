package magis5.magis5challenge.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.service.DrinkService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {

  private final DrinkRepository drinkRepository;

  public Drink findById(String id) {
    return drinkRepository.findById(UUID.fromString(id)).get();
  }

  public Drink save(Drink drink) {
    return drinkRepository.save(drink);
  }

  public List<Drink> findAll() {
    return drinkRepository.findAll();
  }
}
