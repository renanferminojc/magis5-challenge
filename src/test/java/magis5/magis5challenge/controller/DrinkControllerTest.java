package magis5.magis5challenge.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.mapper.DrinkMapperImpl;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.service.impl.DrinkServiceImpl;
import magis5.magis5challenge.utils.DrinkUtils;
import magis5.magis5challenge.utils.FileUtils;
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

@WebMvcTest(controllers = DrinkController.class)
@Import({
  DrinkController.class,
  DrinkServiceImpl.class,
  DrinkMapperImpl.class,
  DrinkUtils.class,
  FileUtils.class
})
class DrinkControllerTest {
  private static final String URL = "/drink";
  private List<Drink> drinks;

  @Autowired private DrinkUtils drinkUtils;

  @Autowired private MockMvc mockMvc;

  @Autowired private FileUtils fileUtils;

  @MockitoBean private DrinkRepository drinkRepository;

  @BeforeEach
  void init() {
    drinks = drinkUtils.newDrinkList();
  }

  @Test
  @DisplayName("GET /drink/1 - It should be able to return a drink")
  void itShouldBeAbleToReturnADrink() throws Exception {
    var response = fileUtils.readResourceFile("drink/get-response-drink--by-id-200.json");
    Drink drink = drinks.getFirst();
    BDDMockito.when(drinkRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(drink));

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/{id}", drinks.getFirst().getId()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /drink/99 - It Should throw not found when drink is not found")
  void itShouldThrowNotFoundWhenDrinkIsNotFound() throws Exception {
    var id = UUID.randomUUID();
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn();

    String expectedMessage = "Drink not found";
    Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(expectedMessage);
  }

  @Test
  @DisplayName("GET /drink/all - It should be able to return all drinks")
  void itShouldBeAbleToReturnAllDrinks() throws Exception {
    var response = fileUtils.readResourceFile("drink/get-response-all-drink-200.json");

    BDDMockito.when(drinkRepository.findAll()).thenReturn(drinks);

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
  @DisplayName("GET /drink/all - It should return empty if has no drink saved")
  void itShouldReturnEmptyIfHasNoDrinkSaved() throws Exception {
    var response = fileUtils.readResourceFile("drink/get-response-empty-drink-200.json");

    BDDMockito.when(drinkRepository.findAll()).thenReturn(Collections.emptyList());

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
  @DisplayName("POST /drink - It should be able to save a drink")
  void itShouldBeAbleToSaveADrink() throws Exception {
    var request = fileUtils.readResourceFile("drink/post-request-save-drink-201.json");
    var response = fileUtils.readResourceFile("drink/post-response-save-drink-201.json");

    BDDMockito.when(drinkRepository.save(ArgumentMatchers.any())).thenReturn(drinks.getFirst());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(URL)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @ParameterizedTest
  @MethodSource("postDrinkBadRequestSource")
  @DisplayName("POST /drink returns bad request when fields are invalid")
  void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("drink/%s".formatted(fileName));

    var mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(URL)
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andReturn();

    var resolvedException = mvcResult.getResolvedException();
    Assertions.assertThat(resolvedException).isNotNull();
    Assertions.assertThat(resolvedException.getMessage()).contains(errors);
  }

  private static List<String> invalidVolumeError() {
    var volumeInvalidError = "The field 'volume' must be greater than or equal to 0.1";
    return List.of(volumeInvalidError);
  }

  private static List<String> allRequiredErrors() {
    var nameRequiredError = "The field 'name' is required";
    var volumeRequiredError = "The field 'volume' is required";
    var drinkRequiredError = "The field 'drink_type' is required";

    return new ArrayList<>(
        List.of(nameRequiredError, volumeRequiredError, drinkRequiredError));
  }

  private static Stream<Arguments> postDrinkBadRequestSource() {
    var allRequiredErrors = allRequiredErrors();
    var volumeInvalidError = invalidVolumeError();

    return Stream.of(
        Arguments.of("post-request-drink-empty-fields-400.json", allRequiredErrors),
        Arguments.of("post-request-drink-blank-fields-400.json", allRequiredErrors),
        Arguments.of("post-request-drink-invalid-drink-type-400.json", volumeInvalidError));
  }
}
