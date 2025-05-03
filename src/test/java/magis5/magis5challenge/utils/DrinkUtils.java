package magis5.magis5challenge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.enumeration.EDrinkType;
import org.springframework.stereotype.Component;

@Component
public class DrinkUtils {
  public List<Drink> newDrinkList() {
    Drink cocaCola =
        createDrink("Coca Cola", "2.0", EDrinkType.NON_ALCOHOLIC)
            .withId(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"));
    Drink pepsiBlack =
        createDrink("Pepsi Black", "1.0", EDrinkType.NON_ALCOHOLIC)
            .withId(UUID.fromString("69aa9995-98a7-495a-a207-f3356b5ff526"));

    return new ArrayList<>(List.of(cocaCola, pepsiBlack));
  }

  private static Drink createDrink(final String name, final String volume, EDrinkType type) {
    return Drink.builder()
        .name(name)
        .volume(new BigDecimal(volume))
        .type(type)
        .createdAt(DateUtils.getDate())
        .build();
  }
}
