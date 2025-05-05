package magis5.magis5challenge.service;

import java.util.List;
import magis5.magis5challenge.domain.History;

public interface HistoryService {
  List<History> findByDrinkId(final String drinkId);

  List<History> findBySectionId(final String sectionId);

  List<History> findAll();
}
