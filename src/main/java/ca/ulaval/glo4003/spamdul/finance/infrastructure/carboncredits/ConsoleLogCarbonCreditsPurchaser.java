package ca.ulaval.glo4003.spamdul.finance.infrastructure.carboncredits;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCredits;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.carboncredits.CarbonCreditsPurchaser;

public class ConsoleLogCarbonCreditsPurchaser implements CarbonCreditsPurchaser {

  @Override
  public void purchase(CarbonCredits credits) {
    System.out.printf("%.2f$ de crédits-carbone ont été achetés.%n", credits.asDouble());
  }
}
