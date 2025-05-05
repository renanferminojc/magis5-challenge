package magis5.magis5challenge.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.ETransaction;
import magis5.magis5challenge.repository.HistoryRepository;
import magis5.magis5challenge.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final HistoryRepository historyRepository;

  public List<History> findByDrinkId(String id) {
    return historyRepository.findByDrinkId(UUID.fromString(id));
  }

  public List<History> findBySectionId(String id) {
    return historyRepository.findBySectionId(UUID.fromString(id));
  }

  public List<History> findAll() {
    return historyRepository.findAll();
  }

  public void save(Section section, Drink drink, ETransaction transactionType, BigDecimal volume) {
    History history =
        History.builder()
            .volume(volume)
            .section(section)
            .drink(drink)
            .transaction(transactionType)
            .build();

    historyRepository.save(history);
  }
}
