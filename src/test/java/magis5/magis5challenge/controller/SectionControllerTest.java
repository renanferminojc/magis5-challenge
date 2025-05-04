package magis5.magis5challenge.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.mapper.SectionMapperImpl;
import magis5.magis5challenge.repository.SectionRepository;
import magis5.magis5challenge.service.impl.SectionServiceImpl;
import magis5.magis5challenge.utils.FileUtils;
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

@WebMvcTest(controllers = SectionController.class)
@Import({
  SectionController.class,
  SectionServiceImpl.class,
  SectionMapperImpl.class,
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

  @BeforeEach
  void init() {
    sections = sectionUtils.newSectionList();
  }

  @Test
  @DisplayName("GET /section/1 - It should be able to return a section")
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
  @DisplayName("GET /section/99 - It Should throw not found when section is not found")
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
}
