package ca.ulaval.glo4003.spamdul.utils.amount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Amount {

  private final BigDecimal value;

  public static Amount valueOf(double value) {
    try {
      return new Amount(BigDecimal.valueOf(value));
    } catch (Exception e) {
      throw new InvalidAmountException();
    }
  }

  public static Amount valueOf(String value) {
    try {
      return new Amount(new BigDecimal(value));
    } catch (Exception e) {
      throw new InvalidAmountException();
    }
  }

  private Amount(BigDecimal value) {
    this.value = value.setScale(2, RoundingMode.HALF_EVEN);
  }

  public double asDouble() {
    return value.doubleValue();
  }

  public boolean isStrictlyNegative() {
    return value.compareTo(BigDecimal.ZERO) < 0;
  }

  public boolean isStrictlyPositive() {
    return value.compareTo(BigDecimal.ZERO) > 0;
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

  public Amount multiply(double other) {
    return new Amount(value.multiply(BigDecimal.valueOf(other)));
  }

  public Amount divide(double other) {
    return new Amount(value.divide(BigDecimal.valueOf(other), RoundingMode.HALF_UP));
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
