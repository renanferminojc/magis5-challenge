package magis5.magis5challenge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.mapper.HistoryMapper;
import magis5.magis5challenge.mapper.HistoryMapperImpl;
import magis5.magis5challenge.repository.HistoryRepository;
import magis5.magis5challenge.service.impl.HistoryServiceImpl;
import magis5.magis5challenge.utils.FileUtils;
import magis5.magis5challenge.utils.HistoryUtils;
import magis5.magis5challenge.utils.SectionUtils;
import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = HistoryController.class)
@Import({
  HistoryController.class,
  HistoryServiceImpl.class,
  HistoryMapperImpl.class,
  FileUtils.class,
  HistoryUtils.class,
})
class HistoryControllerTest {
  private static final String URL = "/history";
  private List<History> histories;

  @Autowired private MockMvc mockMvc;

  @Autowired private FileUtils fileUtils;

  @Autowired private HistoryUtils historyUtils;

  @Autowired private HistoryMapper historyMapper;

  @MockitoBean private HistoryRepository historyRepository;

  @BeforeEach
  void init() {
    histories = historyUtils.newHistoryList();
  }

  @Test
  @DisplayName(
      "GET /history/b4e14200-1d1a-426d-adcf-408c147c6d49 - It should be able to return a history")
  void itShouldBeAbleToReturnAHistory() throws Exception {
    var response = fileUtils.readResourceFile("history/get-response-history-by-id-200.json");
    var id = UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49");

    History history = histories.stream().filter(h -> h.getId().equals(id)).findFirst().get();
    BDDMockito.when(historyRepository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(history));

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/{id}", histories.getFirst().getId()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName(
      "GET /history/section/b4e14200-1d1a-426d-adcf-408c147c6d49 - It should be able to return a history by section id")
  void itShouldBeAbleToReturnAHistoryBySectionId() throws Exception {
    var response =
        fileUtils.readResourceFile("history/get-response-history-by-section-id-200.json");
    var sectionId = UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49");

    List<History> list =
        histories.stream().filter(h -> h.getSection().getId().equals(sectionId)).toList();
    BDDMockito.when(historyRepository.findBySectionId(ArgumentMatchers.any())).thenReturn(list);

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/section/{id}", sectionId))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName(
      "GET /history/drink/b4e14200-1d1a-426d-adcf-408c147c6d49 - It should be able to return a history by drink id")
  void itShouldBeAbleToReturnAHistoryByDrinkId() throws Exception {
    var response = fileUtils.readResourceFile("history/get-response-history-by-drink-id-200.json");
    var drinkId = UUID.fromString(SectionUtils.DEFAULT_UUID);

    List<History> list =
        histories.stream().filter(h -> h.getDrink().getId().equals(drinkId)).toList();
    BDDMockito.when(historyRepository.findByDrinkId(ArgumentMatchers.any())).thenReturn(list);

    mockMvc
        .perform(MockMvcRequestBuilders.get(URL + "/drink/{id}", drinkId))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("GET /history/99 - It Should throw not found when history is not found")
  void itShouldThrowNotFoundWhenHistoryIsNotFound() throws Exception {
    var id = UUID.randomUUID();
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andReturn();

    String expectedMessage = "History not found";
    Assertions.assertThat(mvcResult.getResolvedException().getMessage()).contains(expectedMessage);
  }
}
