package magis5.magis5challenge.repository;

import java.util.Optional;
import java.util.UUID;
import magis5.magis5challenge.domain.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, UUID> {
  @EntityGraph(attributePaths = {"drinkSections", "drinkSections.drink"})
  Optional<Section> findWithStocksAndDrinksById(UUID sectionId);

  Page<Section> findAll(Pageable pageable);
}
