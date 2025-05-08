package magis5.magis5challenge.repository;

import java.util.Optional;
import java.util.UUID;
import magis5.magis5challenge.domain.Section;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, UUID> {
  //  @Query("SELECT s FROM Section s LEFT JOIN FETCH s.drinks WHERE s.id = :sectionId")
  //  Optional<Section> findByIdWithDrinks(@Param("sectionId") UUID sectionId);

  @EntityGraph(attributePaths = {"drinkSections", "drinkSections.drink"})
  Optional<Section> findWithStocksAndDrinksById(UUID sectionId);
}
