package magis5.magis5challenge.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import magis5.magis5challenge.enumeration.ETransaction;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class History {
  @Id
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private BigDecimal volume;

  @ManyToOne(optional = false, cascade = CascadeType.DETACH)
  private Drink drink;

  @ManyToOne(optional = false, cascade = CascadeType.DETACH)
  private Section section;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private ETransaction transaction;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;
}
