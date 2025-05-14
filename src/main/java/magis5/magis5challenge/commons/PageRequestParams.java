package magis5.magis5challenge.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestParams {
  private int page = 0;
  private int size = 10;
  private String sortBy = "createdAt";
  private String sortDirection = "desc";
}
