package magis5.magis5challenge.commons;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableHelper {
  public static Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
    if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
      sortDirection = "DESC";
    }

    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
    return PageRequest.of(page, size, sort);
  }
}
