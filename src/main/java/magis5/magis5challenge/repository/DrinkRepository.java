package magis5.magis5challenge.repository;

import java.util.Optional;
import java.util.UUID;
import magis5.magis5challenge.domain.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrinkRepository extends JpaRepository<Drink, UUID> {
  @Query("SELECT d FROM Drink d LEFT JOIN FETCH d.sections WHERE d.id = :drinkId")
  Optional<Drink> findByIdWithSections(@Param("drinkId") UUID drinkId);
}
