package magis5.magis5challenge.repository;

import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.DrinkSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrinkSectionRepository extends JpaRepository<DrinkSection, UUID> {
  @Query(
      """
    SELECT distinct ds
      FROM DrinkSection ds
      JOIN FETCH ds.section
      JOIN FETCH ds.drink
     WHERE ds.drink.id = :drinkId
  """)
  List<DrinkSection> findBDrinkIdWithSections(@Param("drinkId") UUID drinkId);
}
