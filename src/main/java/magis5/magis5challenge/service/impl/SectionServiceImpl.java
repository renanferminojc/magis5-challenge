package magis5.magis5challenge.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import magis5.magis5challenge.exception.NotFoundException;
import magis5.magis5challenge.mapper.SectionMapper;
import magis5.magis5challenge.repository.SectionRepository;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import magis5.magis5challenge.service.SectionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

  private final SectionRepository sectionRepository;
  private final SectionMapper sectionMapper;

  public SectionGetResponse findById(String id) {
    Section section =
        sectionRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new NotFoundException("Section not found"));
    return sectionMapper.toSectionGetResponse(section);
  }

  public List<SectionGetResponse> findAll() {
    List<Section> sectionList = sectionRepository.findAll();
    return sectionMapper.toSectionGetResponse(sectionList);
  }

  public SectionPostResponse save() {
    Section section =
        Section.builder().stock(BigDecimal.ZERO).drinkType(EDrinkType.NON_ALCOHOLIC).build();
    Section sectionSaved = sectionRepository.save(section);
    return sectionMapper.toSectionPostResponse(sectionSaved);
  }
}
