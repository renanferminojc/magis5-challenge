package magis5.magis5challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DrinkTypeException extends ResponseStatusException {
  public DrinkTypeException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
