package ca.ulaval.glo4003.spamdul.infrastructure.carboncredits;

import ca.ulaval.glo4003.spamdul.entity.carboncredits.CarbonCreditsPurchaser;

public class ConsoleLogCarbonCreditsPurchaser implements CarbonCreditsPurchaser {

  @Override
  public void purchase(double amount) {
    System.out.printf("%.2f$ de crédits-carbone ont été achetés.%n", amount);
  }
}
