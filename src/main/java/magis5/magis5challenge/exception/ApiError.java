package magis5.magis5challenge.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApiError {
  private String timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
}
