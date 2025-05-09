package magis5.magis5challenge.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import magis5.magis5challenge.domain.History;
import magis5.magis5challenge.domain.Section;
import magis5.magis5challenge.enumeration.EDrinkType;
import magis5.magis5challenge.enumeration.ETransaction;
import org.springframework.stereotype.Component;

@Component
public class HistoryUtils {
  public List<History> newHistoryList() {
    return List.of(
        createHistory("b4e14200-1d1a-426d-adcf-408c147c6d49", ETransaction.IN),
        createHistory("69aa9995-98a7-495a-a207-f3356b5ff526", ETransaction.OUT));
  }

  private History createHistory(String id, ETransaction transaction) {
    return History.builder()
        .id(UUID.fromString(id))
        .volume(new BigDecimal("10"))
        .transaction(transaction)
        .drink(createDrink())
        .section(createSection())
        .createdAt(DateUtils.getDate())
        .build();
  }

  private Drink createDrink() {
    return Drink.builder()
        .id(java.util.UUID.fromString(SectionUtils.DEFAULT_UUID))
        .name("Coca Cola")
        .volume(new BigDecimal("10"))
        .type(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .build();
  }

  private static Section createSection() {
    return Section.builder()
        .id(UUID.fromString("b4e14200-1d1a-426d-adcf-408c147c6d49"))
        .stock(new BigDecimal("1"))
        .drinkType(EDrinkType.NON_ALCOHOLIC)
        .createdAt(DateUtils.getDate())
        .updatedAt(DateUtils.getDate())
        .drinkSections(List.of())
        .build();
  }
}
