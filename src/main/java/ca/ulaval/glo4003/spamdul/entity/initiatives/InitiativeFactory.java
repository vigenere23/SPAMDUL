package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeCodeException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeNameException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeFactory {

  private final InitiativeIdFactory initiativeIdFactory;

  public InitiativeFactory(InitiativeIdFactory initiativeIdFactory) {
    this.initiativeIdFactory = initiativeIdFactory;
  }

  public Initiative create(String name, Amount amount) {
    return create(new InitiativeCode(), name, amount);
  }

  public Initiative create(InitiativeCode code, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);
    validateCode(code);
    validateCodeNotReserved(code);

    return new Initiative(initiativeIdFactory.create(), code, name, amount);
  }

  public Initiative create(ReservedInitiativeCode reservedCode, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);
    validateCode(reservedCode.getValue());

    return new Initiative(initiativeIdFactory.create(), reservedCode.getValue(), name, amount);
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

  private void validateCode(InitiativeCode code) {
    if (code == null) {
      throw new InvalidInitiativeCodeException();
    }
  }

  private void validateCodeNotReserved(InitiativeCode code) {
    if (ReservedInitiativeCode.getValues().contains(code)) {
      throw new InvalidInitiativeCodeException();
    }
  }
}
