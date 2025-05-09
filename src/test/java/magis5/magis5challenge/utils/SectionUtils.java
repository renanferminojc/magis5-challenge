package magis5.magis5challenge.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.DrinkSection;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import org.springframework.stereotype.Component;

@Component
public class SectionUtils {

  public static final String DEFAULT_UUID = "b4e14200-1d1a-426d-adcf-408c147c6d49";

  public List<Section> newSectionList() {
    Section section1 =
        Section.builder()
            .id(java.util.UUID.fromString(DEFAULT_UUID))
            .stock(BigDecimal.ZERO)
            .drinkType(EDrinkType.NON_ALCOHOLIC)
            .createdAt(DateUtils.getDate())
            .build();
    Section section2 =
        Section.builder()
            .id(java.util.UUID.fromString(DEFAULT_UUID))
            .stock(BigDecimal.ZERO)
            .drinkType(EDrinkType.NON_ALCOHOLIC)
            .createdAt(DateUtils.getDate())
            .build();
    return new ArrayList<>(List.of(section1, section2));
  }

  public Section sectionWithDrinks() {
    Drink cocaCola = createDrink("Coca Cola", "2.0");
    Drink pepsiBlack = createDrink("Pepsi Black", "0.350");

    DrinkSection drinkSection1 = createDrinkSection(cocaCola, "10.0");
    DrinkSection drinkSection2 = createDrinkSection(pepsiBlack, "100.0");

    BigDecimal totalStock = drinkSection1.getVolume().add(drinkSection2.getVolume());

    return Section.builder()
        .id(java.util.UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
        .stock(totalStock)
        .drinkType(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .updatedAt(DateUtils.getDate())
        .drinkSections(List.of(drinkSection1, drinkSection2))
        .build();
  }

  private Drink createDrink(String name, String volume) {
    return Drink.builder()
        .id(java.util.UUID.fromString(SectionUtils.DEFAULT_UUID))
        .name(name)
        .volume(new BigDecimal(volume))
        .type(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .build();
  }

  private DrinkSection createDrinkSection(Drink drink, String volume) {
    return DrinkSection.builder().drink(drink).volume(new BigDecimal(volume)).build();
  }
}
