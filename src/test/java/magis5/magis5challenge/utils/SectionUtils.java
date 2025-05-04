package magis5.magis5challenge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import org.springframework.stereotype.Component;

@Component
public class SectionUtils {

  public List<Section> newSectionList() {
    Section section1 =
        createSection().withId(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"));
    Section section2 =
        createSection().withId(UUID.fromString("69aa9995-98a7-495a-a207-f3356b5ff526"));

    return new ArrayList<>(List.of(section1, section2));
  }

  public Section sectionWithDrinks() {
    Drink cocaCola =
        Drink.builder()
            .id(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
            .name("Coca Cola")
            .volume(BigDecimal.TEN)
            .type(EDrinkType.NON_ALCOHOLIC)
            .createdAt(DateUtils.getDate())
            .build();

    return createSection()
        .withId(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
        .withDrinks(List.of(cocaCola))
        .withStock(cocaCola.getVolume())
        .withCreatedAt(DateUtils.getDate());
  }

  private static Section createSection() {
    return Section.builder().stock(BigDecimal.ZERO).drinkType(EDrinkType.NON_ALCOHOLIC).build();
  }
}
