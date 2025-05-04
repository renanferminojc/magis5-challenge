package magis5.magis5challenge.mapper;

import java.util.List;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

// #UsesAnotherMapperToMapInsideClass
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DrinkMapper.class})
public interface SectionMapper {

  SectionPostResponse toSectionPostResponse(Section section);

  SectionGetResponse toSectionGetResponse(Section sections);

  List<SectionGetResponse> toSectionGetResponse(List<Section> sections);
}
