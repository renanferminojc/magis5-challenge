package magis5.magis5challenge.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import org.springframework.stereotype.Component;

@Component
public class DrinkSectionUtils {
  public DrinkSection createDrinkSection() {
    Drink cocaCola = createDrink();

    DrinkSection drinkSection =
        DrinkSection.builder().drink(cocaCola).volume(new BigDecimal("10.0")).build();
    Section section = createSection(drinkSection);
    drinkSection.setSection(section);

    return drinkSection;
  }

  private static Section createSection(DrinkSection drinkSection) {
    return Section.builder()
        .id(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
        .stock(new BigDecimal("1"))
        .drinkType(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .updatedAt(DateUtils.getDate())
        .drinkSections(List.of(drinkSection))
        .build();
  }

  private Drink createDrink() {
    return Drink.builder()
        .id(java.util.UUID.fromString(SectionUtils.DEFAULT_UUID))
        .name("Coca Cola")
        .volume(new BigDecimal("2.0"))
        .type(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .build();
  }
}
