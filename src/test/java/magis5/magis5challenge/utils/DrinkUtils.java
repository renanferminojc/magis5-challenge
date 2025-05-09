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
        Drink.builder()
            .id(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
            .name("Coca Cola")
            .volume(new BigDecimal("2.0"))
            .type(EDrinkType.NON_ALCOHOLIC)
            .createdAt(DateUtils.getDate())
            .build();
    Drink pepsiBlack =
        Drink.builder()
            .id(UUID.fromString("69aa9995-98a7-495a-a207-f3356b5ff526"))
            .name("Pepsi Black")
            .volume(new BigDecimal("0.350"))
            .type(EDrinkType.NON_ALCOHOLIC)
            .createdAt(DateUtils.getDate())
            .build();

    return new ArrayList<>(List.of(cocaCola, pepsiBlack));
  }
}
