package magis5.magis5challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import magis5.magis5challenge.enumeration.EDrinkType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class DrinkPostResponse {
    private UUID id;
    private String name;
    private BigDecimal volume;

    // #DifferentNameMapping
    @JsonProperty("drink_type")
    private EDrinkType drinkType;
}
