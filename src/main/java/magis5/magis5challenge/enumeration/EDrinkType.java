package magis5.magis5challenge.enumeration;

import java.math.BigDecimal;

public enum EDrinkType {
  ALCOHOLIC(BigDecimal.valueOf(500)),
  NON_ALCOHOLIC(BigDecimal.valueOf(400)),
  NONE(BigDecimal.ZERO);

  private final BigDecimal maxVolume;

  EDrinkType(BigDecimal maxVolume) {
    this.maxVolume = maxVolume;
  }

  public BigDecimal getMaxVolume() {
    return maxVolume;
  }
}
