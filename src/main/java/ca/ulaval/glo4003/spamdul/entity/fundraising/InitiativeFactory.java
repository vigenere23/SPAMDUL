package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;

public class InitiativeFactory {

  public Initiative create(String name, double amount) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeName("A name must be provided");
    }

    if (amount <= 0) {
      throw new InvalidInitiativeAmount("Amount must be positive");
    }

    return new Initiative(new InitiativeId(), name, amount);
  }
}
