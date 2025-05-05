package magis5.magis5challenge.service;

import java.math.BigDecimal;
import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.ETransaction;

public interface HistoryService {
  List<History> findByDrinkId(final String drinkId);

  List<History> findBySectionId(final String sectionId);

  List<History> findAll();

  void save(Section section, Drink drink, ETransaction transactionType, BigDecimal volume);
}
