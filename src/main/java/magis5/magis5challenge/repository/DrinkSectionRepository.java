package magis5.magis5challenge.repository;

import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.DrinkSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkSectionRepository extends JpaRepository<DrinkSection, UUID> {
  List<DrinkSection> findByDrinkId(UUID drinkId);
}
