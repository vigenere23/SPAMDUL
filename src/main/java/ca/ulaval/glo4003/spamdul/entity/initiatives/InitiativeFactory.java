package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeName;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeFactory {

  public Initiative create(String name, Amount amount) {
    InitiativeCode code = new InitiativeCode();
    return create(code, name, amount);
  }

  public Initiative create(InitiativeCode code, String name, Amount amount) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeName("A name must be provided");
    }

    if (amount.isZero() || amount.isStrictlyNegative()) {
      throw new InvalidInitiativeAmount("Amount must be greather than zero");
    }

    if (code == null) {
      code = new InitiativeCode();
    }

    return new Initiative(new InitiativeId(), code, name, amount);
  }
}
