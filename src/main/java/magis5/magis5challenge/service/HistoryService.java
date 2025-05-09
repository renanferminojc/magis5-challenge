package magis5.magis5challenge.service;

import java.math.BigDecimal;
import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.ETransaction;
import magis5.magis5challenge.response.HistoryGetResponse;

public interface HistoryService {
  List<HistoryGetResponse> findByDrinkId(final String drinkId);

  List<HistoryGetResponse> findBySectionId(final String sectionId);

  List<HistoryGetResponse> findAll();

  void save(Section section, Drink drink, ETransaction transactionType, BigDecimal volume);

  HistoryGetResponse findById(final String id);
}
