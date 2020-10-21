package ca.ulaval.glo4003.spamdul.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {

  private final BigDecimal value;

  public static Amount valueOf(int value) {
    return new Amount(BigDecimal.valueOf(value));
  }

  public static Amount valueOf(double value) {
    return new Amount(BigDecimal.valueOf(value));
  }

  private Amount(BigDecimal value) {
    this.value = value.setScale(2, RoundingMode.HALF_EVEN);
  }

  public double asDouble() {
    return value.doubleValue();
  }

  public boolean isNegative() {
    return value.compareTo(BigDecimal.ZERO) < 0;
  }

  public boolean isZero() {
    return value.compareTo(BigDecimal.ZERO) == 0;
  }
}
