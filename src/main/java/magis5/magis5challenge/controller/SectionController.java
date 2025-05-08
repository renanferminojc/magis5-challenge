package magis5.magis5challenge.controller;

import static org.springframework.http.HttpStatus.CREATED;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magis5.magis5challenge.request.PostRequestSectionHoldDrink;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import magis5.magis5challenge.response.SectionWithDrinksResponse;
import magis5.magis5challenge.service.SectionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("section")
public class SectionController {

  private final SectionService sectionService;

  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SectionGetResponse> findById(@PathVariable String id) {
    log.info("Find a sections - {}", id);

    SectionGetResponse section = sectionService.findById(id);

    return ResponseEntity.ok(section);
  }

  @GetMapping(path = "{id}/drinks", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SectionWithDrinksResponse> findByIdWithDrinks(@PathVariable String id) {
    log.info("Find a section with drinks - {}", id);

    SectionWithDrinksResponse section = sectionService.findByIdWithDrinks(id);

    return ResponseEntity.ok(section);
  }

  @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<SectionGetResponse>> findAll() {
    log.info("Find all sections");

    List<SectionGetResponse> sectionList = sectionService.findAll();

    return ResponseEntity.ok(sectionList);
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SectionPostResponse> save() {
    log.info("Create a section");

    SectionPostResponse section = sectionService.save();

    return ResponseEntity.status(CREATED).body(section);
  }

  @PutMapping(
      path = "{sectionId}/drink",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SectionWithDrinksResponse> manageSection(
      @PathVariable String sectionId, @RequestBody @Valid PostRequestSectionHoldDrink requestBody) {
    log.info("Hold a drink in a section");

    var section = sectionService.manageSection(sectionId, requestBody);

    return ResponseEntity.ok(section);
  }
}
