package magis5.magis5challenge.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {
  public LocalDateTime getDate() {
    var dateTime = "2025-04-13T17:15:13.4529147";
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    return LocalDateTime.parse(dateTime, formatter);
  }
}
