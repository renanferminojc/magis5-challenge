package magis5.magis5challenge.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
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
public class DrinkPostRequest {
  @NotBlank(message = "The field 'name' is required")
  private String name;

  @NotNull(message = "The field 'volume' is required")
  @DecimalMin(value = "0.1", message = "The field 'volume' must be greater than or equal to 0.1")
  private BigDecimal volume;

  @JsonProperty("drink_type")
  @NotBlank(message = "The field 'drink_type' is required")
  @Pattern(
      regexp = "ALCOHOLIC|NON_ALCOHOLIC",
      message = "Invalid drink type. Valid values are: ALCOHOLIC, NON_ALCOHOLIC")
  private String drinkType;
}
