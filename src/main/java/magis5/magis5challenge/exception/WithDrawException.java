package magis5.magis5challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WithDrawException extends ResponseStatusException {
  public WithDrawException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
