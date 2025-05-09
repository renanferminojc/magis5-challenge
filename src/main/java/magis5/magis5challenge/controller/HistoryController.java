package magis5.magis5challenge.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magis5.magis5challenge.response.HistoryGetResponse;
import magis5.magis5challenge.service.HistoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("history")
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HistoryGetResponse> findById(@PathVariable String id) {
    log.info("Find a history by id- {}", id);

    HistoryGetResponse history = historyService.findById(id);

    return ResponseEntity.ok(history);
  }

  @GetMapping(path = "drink/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<HistoryGetResponse>> findByDrinkId(@PathVariable String id) {
    log.info("Find a history based on drink id- {}", id);

    List<HistoryGetResponse> history = historyService.findByDrinkId(id);

    return ResponseEntity.ok(history);
  }

  @GetMapping(path = "section/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<HistoryGetResponse>> findBySectionId(@PathVariable String id) {
    log.info("Find a history based on section id- {}", id);

    List<HistoryGetResponse> history = historyService.findBySectionId(id);

    return ResponseEntity.ok(history);
  }

  @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<HistoryGetResponse>> findAll() {
    log.info("Find all histories");

    List<HistoryGetResponse> historyList = historyService.findAll();

    return ResponseEntity.ok(historyList);
  }
}
