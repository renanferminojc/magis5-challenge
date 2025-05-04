package magis5.magis5challenge.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRequestSectionHoldDrink {
  @NotBlank(message = "The field 'drink_id' is required")
  @JsonProperty("drink_id")
  private String drinkId;

  @NotNull(message = "The field 'qty' is required")
  @Min(value = 1, message = "The field 'qty' must be greater than or equal to 1")
  @JsonProperty("qty")
  private BigDecimal qty;

  @NotBlank(message = "The field 'operation_type' is required")
  @Pattern(regexp = "IN|OUT", message = "Invalid transaction type. Valid values are: IN, OUT")
  @JsonProperty("transaction_type")
  private String transactionType;
}
