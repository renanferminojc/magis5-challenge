package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DrinkWithSectionsResponse {
  @JsonProperty("drink_id")
  private String drinkId;

  private String name;
  private BigDecimal volume;
  private List<SectionGetResponse> sections;
}
