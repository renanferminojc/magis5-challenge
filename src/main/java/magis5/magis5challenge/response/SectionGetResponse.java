package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import magis5.magis5challenge.enumeration.EDrinkType;

@Getter
@Builder
public class SectionGetResponse {
  private UUID id;
  private BigDecimal stock;

  @JsonProperty("drink_Type")
  private EDrinkType drinkType;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;
}
