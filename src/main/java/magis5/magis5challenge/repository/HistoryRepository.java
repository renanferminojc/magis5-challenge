package magis5.magis5challenge.repository;

import java.util.List;
import java.util.UUID;
import magis5.magis5challenge.domain.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, UUID> {
  List<History> findBySectionId(UUID sectionId);

  List<History> findByDrinkId(UUID drinkId);

  Page<History> findAll(Pageable pageable);
}
