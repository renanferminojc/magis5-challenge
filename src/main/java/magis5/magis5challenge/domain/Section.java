package magis5.magis5challenge.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "section_drink",
      joinColumns = @JoinColumn(name = "section_id"),
      inverseJoinColumns = @JoinColumn(name = "drink_id"))
  private List<Drink> drinks;
}
