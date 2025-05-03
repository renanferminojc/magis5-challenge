package magis5.magis5challenge.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class DrinkPostRequest {
    private String name;
    private BigDecimal volume;

    @JsonProperty("drink_type")
    private String drinkType;
}
