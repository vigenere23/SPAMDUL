package ca.ulaval.glo4003.spamdul.entity.carboncredits;

import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.math.BigDecimal;

public class CarbonCredits {

  private static final double CARBON_CREDIT_TO_AMOUNT_RATIO = 21.81;
  private final BigDecimal value;

  public static CarbonCredits valueOf(double value) {
    return new CarbonCredits(BigDecimal.valueOf(value));
  }

  public static CarbonCredits valueOf(Amount amount) {
    double value = amount.divide(CARBON_CREDIT_TO_AMOUNT_RATIO).asDouble();
    return new CarbonCredits(BigDecimal.valueOf(value));
  }

  public CarbonCredits(BigDecimal value) {
    this.value = value;
  }

  public double asDouble() {
    return value.doubleValue();
  }
}
