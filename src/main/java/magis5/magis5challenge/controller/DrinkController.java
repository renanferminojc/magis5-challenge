package magis5.magis5challenge.controller;

import static org.springframework.http.HttpStatus.*;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import magis5.magis5challenge.response.DrinkWithSectionsResponse;
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
  public ResponseEntity<DrinkGetResponse> findById(@PathVariable String id) {
    log.info("Find a drinks - {}", id);

    DrinkGetResponse drink = drinkService.findById(id);

    return ResponseEntity.ok(drink);
  }

  @GetMapping(path = "{drinkId}/sections", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DrinkWithSectionsResponse> drinkWithSections(@PathVariable String drinkId) {
    log.info("Find a drink by id with sections - {}", drinkId);

    DrinkWithSectionsResponse drinkWithSections = drinkService.findDrinkWithSections(drinkId);

    return ResponseEntity.ok(drinkWithSections);
  }

  @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DrinkGetResponse>> findAll() {
    log.info("Find all drinks");

    List<DrinkGetResponse> drinkList = drinkService.findAll();

    return ResponseEntity.ok(drinkList);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DrinkPostResponse> save(@RequestBody @Valid DrinkPostRequest request) {
    log.info("Create a drink");

    DrinkPostResponse drink = drinkService.save(request);

    return ResponseEntity.status(CREATED).body(drink);
  }
}
