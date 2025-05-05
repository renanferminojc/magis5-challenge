package magis5.magis5challenge.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.History;
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
}
