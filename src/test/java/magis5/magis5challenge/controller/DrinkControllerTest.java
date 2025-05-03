package magis5.magis5challenge.controller;

import java.util.List;
import java.util.Optional;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.mapper.DrinkMapperImpl;
import magis5.magis5challenge.repository.DrinkRepository;
import magis5.magis5challenge.service.impl.DrinkServiceImpl;
import magis5.magis5challenge.utils.DrinkUtils;
import magis5.magis5challenge.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DrinkController.class)
@Import({DrinkController.class, DrinkServiceImpl.class, DrinkMapperImpl.class, DrinkUtils.class, FileUtils.class})
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
}
