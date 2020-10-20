package ca.ulaval.glo4003.spamdul.entity.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions.InvalidInitiativeName;
import ca.ulaval.glo4003.spamdul.utils.Amount;

public class InitiativeFactory {

  public Initiative create(String name, Amount amount) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeName("A name must be provided");
    }

    if (amount.isZero() || amount.isNegative()) {
      throw new InvalidInitiativeAmount("Amount must be greather than zero");
    }

    return new Initiative(new InitiativeId(), name, amount);
  }
}
