package magis5.magis5challenge.commons;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {
  public boolean hasBeen24HoursSinceUpdate(LocalDateTime updatedAt) {
    LocalDateTime now = LocalDateTime.now();
    long hoursDifference = ChronoUnit.HOURS.between(updatedAt, now);
    return hoursDifference >= 24;
  }
}
