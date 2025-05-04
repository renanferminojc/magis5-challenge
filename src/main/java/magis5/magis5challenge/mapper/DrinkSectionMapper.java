package magis5.magis5challenge.mapper;

import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.response.DrinkSectionResponse;
import magis5.magis5challenge.response.SectionDrinkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DrinkMapper.class, SectionMapper.class})
public interface DrinkSectionMapper {

  // #AvoidCircularDependencyMapStruct
  DrinkSectionResponse toDrinkSectionResponse(Drink drink, List<Section> sections);

  SectionDrinkResponse toSectionDrinkResponse(Section section, List<Drink> drinks);
}
