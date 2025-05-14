package magis5.magis5challenge.commons;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableHelper {
  public static Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
    sortDirection = getSortDirection(sortDirection);

    Sort sort = getSort(sortDirection, sortBy);
    return PageRequest.of(page, size, sort);
  }

  public static Pageable createPageable(PageRequestParams requestParams) {
    var sortDirection = getSortDirection(requestParams.getSortDirection());

    Sort sort = getSort(sortDirection, requestParams.getSortBy());
    return PageRequest.of(requestParams.getPage(), requestParams.getSize(), sort);
  }

  private static Sort getSort(String sortDirection, String requestParams) {
    return Sort.by(Sort.Direction.fromString(sortDirection), requestParams);
  }

  private static String getSortDirection(String sortDirection) {
    if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
      sortDirection = "DESC";
    }
    return sortDirection;
  }
}
