package magis5.magis5challenge.mapper;

import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.response.DrinkSectionResponse;
import magis5.magis5challenge.response.SectionWithDrinksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DrinkMapper.class, SectionMapper.class})
public interface DrinkSectionMapper {

  // #AvoidCircularDependencyMapStruct
  @Mapping(target = "drinkVolume", source = "drink.volume")
  @Mapping(target = "drinkId", source = "drink.id")
  @Mapping(target = "drinkType", source = "drink.type")
  DrinkSectionResponse toDrinkSectionResponse(Drink drink, List<Section> sections);

  SectionWithDrinksResponse toSectionDrinkResponse(Section section, List<Drink> drinks);

  @Mapping(target = "name", source = "drink.name")
  @Mapping(target = "drinkId", source = "drink.id")
  @Mapping(target = "drinkType", source = "drink.type")
  @Mapping(target = "drinkVolume", source = "drink.volume")
  @Mapping(target = "totalDrinkVolume", source = "volume")
  DrinkSectionResponse toDrinkSectionResponse(DrinkSection drinkSection);

  List<DrinkSectionResponse> toDrinkSectionResponse(List<DrinkSection> drinkSections);
}
