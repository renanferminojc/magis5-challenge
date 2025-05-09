package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import magis5.magis5challenge.enumeration.ETransaction;

@Setter
@Getter
@Builder
public class HistoryGetResponse {
  private UUID id;
  private BigDecimal volume;
  private DrinkGetResponse drink;
  private SectionGetResponse section;
  private ETransaction transaction;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;
}
