package magis5.magis5challenge.mapper;

import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.request.DrinkPostRequest;
import magis5.magis5challenge.response.DrinkGetResponse;
import magis5.magis5challenge.response.DrinkPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DrinkMapper {
  @Mapping(target = "type", source = "drinkType")
  Drink toDrink(DrinkPostRequest drinkPostRequest);

  // #DifferentNameMapping
  @Mapping(target = "drinkType", source = "type")
  DrinkPostResponse toDrinkPostResponse(Drink drink);

  @Mapping(target = "drinkType", source = "type")
  DrinkGetResponse toDrinkGetResponse(Drink drinks);

  List<DrinkGetResponse> toDrinkGetResponse(List<Drink> drinks);
}
