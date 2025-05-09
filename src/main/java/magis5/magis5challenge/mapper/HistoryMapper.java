package magis5.magis5challenge.mapper;

import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.response.HistoryGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HistoryMapper {
  @Mapping(target = "drink.drinkType", source = "history.drink.type")
  HistoryGetResponse toHistoryGetResponse(History history);
}
