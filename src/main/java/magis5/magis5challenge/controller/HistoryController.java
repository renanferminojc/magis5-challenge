package magis5.magis5challenge.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magis5.magis5challenge.domain.History;
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

  @GetMapping(path = "drink/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<History>> findByDrinkId(@PathVariable String id) {
    log.info("Find a history based on drink id- {}", id);

    List<History> history = historyService.findByDrinkId(id);

    return ResponseEntity.ok(history);
  }

  @GetMapping(path = "section/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<History>> findBySectionId(@PathVariable String id) {
    log.info("Find a history based on section id- {}", id);

    List<History> history = historyService.findBySectionId(id);

    return ResponseEntity.ok(history);
  }

  @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<History>> findAll() {
    log.info("Find all histories");

    List<History> historyList = historyService.findAll();

    return ResponseEntity.ok(historyList);
  }
}
