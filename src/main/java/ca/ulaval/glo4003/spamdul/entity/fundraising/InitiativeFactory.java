package ca.ulaval.glo4003.spamdul.entity.fundraising;

public class InitiativeFactory {

  public Initiative create(String name, double amount) {
    if (name == null) {
      throw new RuntimeException("A name must be provided");
    }

    if (amount <= 0) {
      throw new RuntimeException("Amount must be positive");
    }

    return new Initiative(new InitiativeId(), name, amount);
  }
}
