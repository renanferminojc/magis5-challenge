package magis5.magis5challenge.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.mapper.DrinkMapperImpl;
import magis5.magis5challenge.mapper.SectionMapperImpl;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.repository.SectionRepository;
import magis5.magis5challenge.service.impl.SectionServiceImpl;
import magis5.magis5challenge.utils.FileUtils;
import magis5.magis5challenge.utils.SectionUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = SectionController.class)
@Import({
  SectionController.class,
  SectionServiceImpl.class,
  SectionMapperImpl.class,
  DrinkMapperImpl.class,
  SectionUtils.class,
  FileUtils.class
})
class SectionControllerTest {
  private static final String URL = "/section";
  private List<Section> sections;

  @Autowired private SectionUtils sectionUtils;

  @Autowired private MockMvc mockMvc;

  @Autowired private FileUtils fileUtils;

  @MockitoBean private SectionRepository sectionRepository;

  @MockitoBean private DrinkRepository drinkRepository;

  @BeforeEach
  void init() {
    sections = sectionUtils.newSectionList();
  }

  @Test
  @DisplayName(
      "GET /section/b4e14200-1d1a-426d-adcf-408c147c6d49 - It should be able to return a section")
  void itShouldBeAbleToReturnASection() throws Exception {
    var response = fileUtils.readResourceFile("section/get-response-section-by-id-200.json");
    Section section = sections.getFirst();
    BDDMockito.when(sectionRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(section));

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/{id}", sections.getFirst().getId()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName(
      "GET /section/b4e14200-1d1a-426d-adcf-408c147c6d49/drinks - It should be able to return a section with drinks")
  void itShouldBeAbleToReturnASectionWithDrinks() throws Exception {
    var response =
        fileUtils.readResourceFile("section/get-response-section-by-id-with-drinks-200.json");
    BDDMockito.when(sectionRepository.findByIdWithDrinks(ArgumentMatchers.any()))
        .thenReturn(Optional.ofNullable(sectionUtils.sectionWithDrinks()));

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/{id}/drinks", sections.getFirst().getId()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName(
      "GET /section/b4e14200-1d1a-426d-adcf-99999999999 - It Should throw not found when section is not found")
  void itShouldThrowNotFoundWhenSectionIsNotFound() throws Exception {
    var id = UUID.randomUUID();
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn();

    String expectedMessage = "Section not found";
    Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(expectedMessage);
  }

  @Test
  @DisplayName("GET /section/all - It should be able to return all sections")
  void itShouldBeAbleToReturnAllSections() throws Exception {
    var response = fileUtils.readResourceFile("section/get-response-all-section-200.json");

    BDDMockito.when(sectionRepository.findAll()).thenReturn(sections);

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/all"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /section/all - It should return empty if has no section saved")
  void itShouldReturnEmptyIfHasNoSectionSaved() throws Exception {
    var response = fileUtils.readResourceFile("section/get-response-empty-section-200.json");

    BDDMockito.when(sectionRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/all"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("POST /section - It should be able to save a section")
  void itShouldBeAbleToSaveASection() throws Exception {
    var response = fileUtils.readResourceFile("section/post-response-save-section-201.json");

    BDDMockito.when(sectionRepository.save(ArgumentMatchers.any())).thenReturn(sections.getFirst());

    mockMvc
        .perform(MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @ParameterizedTest
  @MethodSource("putSectionDrinkBadRequestSource")
  @DisplayName("POST /section/{section_id}/drink returns bad request when fields are invalid")
  void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("section/%s".formatted(fileName));
    var section = sections.getFirst();

    var mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put(URL + "/{id}/drink", section.getId())
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn();

    var resolvedException = mvcResult.getResolvedException();
    Assertions.assertThat(resolvedException).isNotNull();
    Assertions.assertThat(resolvedException.getMessage()).contains(errors);
  }

  private static List<String> invalidTransactionErrors() {
    var emailInvalidError = "Invalid transaction type. Valid values are: IN, OUT";
    return List.of(emailInvalidError);
  }

  private static List<String> allRequiredErrors() {
    var drinkIdRequiredError = "The field 'drink_id' is required";
    var qtyRequiredError = "The field 'qty' is required";

    return new ArrayList<>(List.of(drinkIdRequiredError, qtyRequiredError));
  }

  private static Stream<Arguments> putSectionDrinkBadRequestSource() {
    var allRequiredErrors = allRequiredErrors();
    var invalidTransactionErrors = invalidTransactionErrors();

    return Stream.of(
        Arguments.of("put-request-section-drink-empty-fields-400.json", allRequiredErrors),
        Arguments.of("put-request-section-drink-blank-fields-400.json", allRequiredErrors),
        Arguments.of(
            "put-request-section-drink-wrong-transaction-type-fields-400.json",
            invalidTransactionErrors));
  }
}
