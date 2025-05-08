package magis5.magis5challenge.mapper;

import java.util.List;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.response.SectionGetResponse;
import magis5.magis5challenge.response.SectionPostResponse;
import magis5.magis5challenge.response.SectionWithDrinksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

// #UsesAnotherMapperToMapInsideClass
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DrinkSectionMapper.class})
public interface SectionMapper {

  SectionPostResponse toSectionPostResponse(Section section);

  SectionGetResponse toSectionGetResponse(Section section);

  List<SectionGetResponse> toSectionGetResponse(List<Section> sections);

  @Mapping(target = "sectionId", source = "id")
  @Mapping(target = "totalStock", source = "stock")
  SectionWithDrinksResponse toSectionDrinkResponse(Section section);
}
