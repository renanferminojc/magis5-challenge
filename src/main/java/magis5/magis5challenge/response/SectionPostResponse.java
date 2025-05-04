package magis5.magis5challenge.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import magis5.magis5challenge.enumeration.EDrinkType;

@Getter
@Builder
public class SectionPostResponse {
  private UUID id;
  private BigDecimal stock;
  private EDrinkType drinkType;
}
