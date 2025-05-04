package magis5.magis5challenge.response;

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
  private EDrinkType drinkType;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<DrinkGetResponse> drinks;
}
