package ca.ulaval.glo4003.spamdul.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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

  public Amount add(Amount other) {
    return new Amount(value.add(other.value));
  }

  public Amount subtract(Amount other) {
    return new Amount(value.subtract(other.value));
  }

  public Amount multiply(Amount other) {
    return new Amount(value.multiply(other.value));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Amount amount = (Amount) o;
    return Objects.equals(value, amount.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
