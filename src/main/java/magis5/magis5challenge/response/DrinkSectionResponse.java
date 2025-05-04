package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import magis5.magis5challenge.enumeration.EDrinkType;

@Getter
@Setter
@Builder
public class DrinkSectionResponse {
  private UUID id;
  private String name;
  private BigDecimal volume;

  @JsonProperty("drink_type")
  private EDrinkType drinkType;

  @JsonManagedReference private List<SectionGetResponse> sections;
}
