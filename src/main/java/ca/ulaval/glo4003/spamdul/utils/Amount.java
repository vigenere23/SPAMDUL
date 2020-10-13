package ca.ulaval.glo4003.spamdul.utils;

import java.math.BigDecimal;

public class Amount {

  private final BigDecimal amount;

  public Amount(double amount) {
    this.amount = BigDecimal.valueOf(amount);
  }

  public double getAmount() {
    return amount.doubleValue();
  }
}
