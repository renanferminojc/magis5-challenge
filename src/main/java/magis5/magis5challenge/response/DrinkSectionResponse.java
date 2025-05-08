package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import magis5.magis5challenge.enumeration.EDrinkType;

@Getter
@Setter
@Builder
public class DrinkSectionResponse {
  @JsonProperty("drink_id")
  private UUID drinkId;

  private String name;

  @JsonProperty("drink_volume")
  private BigDecimal drinkVolume;

  @JsonProperty("total_drink_volume")
  private BigDecimal totalDrinkVolume;

  @JsonProperty("drink_type")
  private EDrinkType drinkType;
}
