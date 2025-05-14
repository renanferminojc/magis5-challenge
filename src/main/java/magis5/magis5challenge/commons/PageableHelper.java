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

  public static Pageable createPageable(PageRequestParams requestParams) {
    if (!"ASC".equalsIgnoreCase(requestParams.getSortDirection())
        && !"DESC".equalsIgnoreCase(requestParams.getSortDirection())) {
      requestParams.setSortDirection("DESC");
    }

    Sort sort =
        Sort.by(
            Sort.Direction.fromString(requestParams.getSortDirection()), requestParams.getSortBy());
    return PageRequest.of(requestParams.getPage(), requestParams.getSize(), sort);
  }
}
