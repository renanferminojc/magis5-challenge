package magis5.magis5challenge.repository;

import java.util.UUID;
import magis5.magis5challenge.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, UUID> {}
