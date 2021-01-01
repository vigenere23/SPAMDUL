package ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.math.BigDecimal;
import java.util.Objects;

public class CarbonCredits {

  private static final double CARBON_CREDIT_TO_AMOUNT_RATIO = 21.81;
  private final BigDecimal credits;

  public static CarbonCredits valueOf(double credits) {
    return new CarbonCredits(BigDecimal.valueOf(credits));
  }

  public static CarbonCredits valueOf(Amount amount) {
    double credits = amount.divide(CARBON_CREDIT_TO_AMOUNT_RATIO).asDouble();
    return new CarbonCredits(BigDecimal.valueOf(credits));
  }

  public CarbonCredits(BigDecimal credits) {
    this.credits = credits;
  }

  public double asDouble() {
    return credits.doubleValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarbonCredits credits = (CarbonCredits) o;

    return Objects.equals(this.credits, credits.credits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(credits);
  }
}
