package magis5.magis5challenge.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.commons.PageableHelper;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.ETransaction;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.mapper.HistoryMapper;
import magis5.magis5challenge.repository.HistoryRepository;
import magis5.magis5challenge.response.HistoryGetResponse;
import magis5.magis5challenge.service.HistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final HistoryRepository historyRepository;
  private final HistoryMapper historyMapper;

  public HistoryGetResponse findById(String id) {
    History history =
        historyRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException("History not found"));
    return historyMapper.toHistoryGetResponse(history);
  }

  public List<HistoryGetResponse> findByDrinkId(String id) {
    List<History> history = historyRepository.findByDrinkId(UUID.fromString(id));
    return history.stream().map(historyMapper::toHistoryGetResponse).toList();
  }

  public List<HistoryGetResponse> findBySectionId(String id) {
    List<History> history = historyRepository.findBySectionId(UUID.fromString(id));
    return history.stream().map(historyMapper::toHistoryGetResponse).toList();
  }

  public List<HistoryGetResponse> findAll(int page, int size, String sortBy, String sortDirection) {
    Pageable pageable = PageableHelper.createPageable(page, size, sortBy, sortDirection);
    List<History> histories = historyRepository.findAll(pageable).getContent();
    return histories.stream().map(historyMapper::toHistoryGetResponse).toList();
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
