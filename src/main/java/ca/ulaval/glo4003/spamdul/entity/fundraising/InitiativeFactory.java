package ca.ulaval.glo4003.spamdul.entity.fundraising;

public class InitiativeFactory {

  public Initiative create(String name, double amount) {
    return new Initiative(new InitiativeId(), name, amount);
  }
}
