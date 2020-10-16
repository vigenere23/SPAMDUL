package ca.ulaval.glo4003.spamdul.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {

  private final BigDecimal amount;

  public Amount(double amount) {
    this.amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN);
  }

  public double getAmount() {
    return amount.doubleValue();
  }
}
