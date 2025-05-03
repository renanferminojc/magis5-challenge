package magis5.magis5challenge.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.service.DrinkService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("drink")
public class DrinkController {

  private final DrinkService drinkService;

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Drink> findById(@PathVariable String id) {
    log.info("Find a drinks - {}", id);

    Drink drink = drinkService.findById(id);

    return ResponseEntity.ok(drink);
  }

  @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Drink>> findAll() {
    log.info("Find all drinks");

    List<Drink> drinkList = drinkService.findAll();

    return ResponseEntity.ok(drinkList);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Drink> save(@RequestBody Drink drink) {
    log.info("Create a drink");

    Drink drinkSaved = drinkService.save(drink);

    return ResponseEntity.status(CREATED).body(drinkSaved);
  }
}
