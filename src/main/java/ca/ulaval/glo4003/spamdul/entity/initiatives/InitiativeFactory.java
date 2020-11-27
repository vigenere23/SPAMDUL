package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeCodeException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeNameException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeFactory {

  public Initiative create(String name, Amount amount) {
    InitiativeCode code = new InitiativeCode();
    return create(code, name, amount);
  }

  public Initiative create(InitiativeCode code, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);
    validateCodeNotReserved(code);

    return new Initiative(new InitiativeId(), code, name, amount);
  }

  public Initiative createWithReservedCode(ReservedInitiativeCode code, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);

    return new Initiative(new InitiativeId(), code.getValue(), name, amount);
  }

  private void validateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeNameException("A name must be provided");
    }
  }

  private void validateAmount(Amount amount) {
    if (amount.isZero() || amount.isStrictlyNegative()) {
      throw new InvalidInitiativeAmountException("Amount must be greather than zero");
    }
  }

  private void validateCodeNotReserved(InitiativeCode code) {
    if (ReservedInitiativeCode.getValues().contains(code)) {
      throw new InvalidInitiativeCodeException();
    }
  }
}
