package magis5.magis5challenge.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import magis5.magis5challenge.enumeration.EDrinkType;
import org.hibernate.annotations.CreationTimestamp;

@With
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Section {
  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private BigDecimal stock;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private EDrinkType drinkType;

  @CreationTimestamp private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @JsonManagedReference
  @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DrinkSection> drinkSections = new ArrayList<>();

  @Transient
  public List<Drink> getDrinks() {
    return drinkSections.stream().map(DrinkSection::getDrink).toList();
  }

  public void addStock(Drink drink, BigDecimal qty) {
    DrinkSection ds =
        drinkSections.stream()
            .filter(s -> s.getDrink().equals(drink))
            .findFirst()
            .orElseGet(
                () -> {
                  DrinkSection drinkSection =
                      DrinkSection.builder().drink(drink).section(this).build();
                  drinkSections.add(drinkSection);
                  return drinkSection;
                });
    ds.setVolume(ds.getVolume() == null ? qty : ds.getVolume().add(qty));
  }

  public boolean sectionTypeIsNotEqualDrinkType(EDrinkType drinkType) {
    return this.drinkType.equals(drinkType);
  }
}
