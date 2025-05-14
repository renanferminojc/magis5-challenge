package magis5.magis5challenge.repository;

import jakarta.annotation.Nonnull;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, UUID> {

  @Nonnull
  Page<Drink> findAll(Pageable pageable);
}
