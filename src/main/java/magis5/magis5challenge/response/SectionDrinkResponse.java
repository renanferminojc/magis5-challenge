package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import magis5.magis5challenge.enumeration.EDrinkType;

@Getter
@Setter
@Builder
public class SectionDrinkResponse {
  private UUID id;
  private BigDecimal stock;

  @JsonProperty("drink_type")
  private EDrinkType drinkType;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  private List<DrinkGetResponse> drinks;
}
