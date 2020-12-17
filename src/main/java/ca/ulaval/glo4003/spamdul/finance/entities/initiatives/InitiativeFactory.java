package ca.ulaval.glo4003.spamdul.finance.entities.initiatives;

import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions.InvalidInitiativeCodeException;
import ca.ulaval.glo4003.spamdul.finance.entities.initiatives.exceptions.InvalidInitiativeNameException;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InitiativeFactory {

  private final InitiativeIdFactory initiativeIdFactory;
  private final InitiativeCodeFactory initiativeCodeFactory;

  public InitiativeFactory(InitiativeIdFactory initiativeIdFactory,
                           InitiativeCodeFactory initiativeCodeFactory) {
    this.initiativeIdFactory = initiativeIdFactory;
    this.initiativeCodeFactory = initiativeCodeFactory;
  }

  public Initiative create(String name, Amount amount) {
    validateName(name);
    validateAmount(amount);

    return new Initiative(initiativeIdFactory.create(), initiativeCodeFactory.create(), name, amount);
  }

  public Initiative create(InitiativeCode code, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);
    validateCodeNotReserved(code);

    return new Initiative(initiativeIdFactory.create(), code, name, amount);
  }

  public Initiative create(ReservedInitiativeCode reservedCode, String name, Amount amount) {
    validateName(name);
    validateAmount(amount);

    return new Initiative(initiativeIdFactory.create(), reservedCode.getValue(), name, amount);
  }

  private void validateName(String name) {
    if (name == null || name.isEmpty()) {
      throw new InvalidInitiativeNameException();
    }
  }

  private void validateAmount(Amount amount) {
    if (amount.isZero() || amount.isStrictlyNegative()) {
      throw new InvalidInitiativeAmountException();
    }
  }

  private void validateCodeNotReserved(InitiativeCode code) {
    if (ReservedInitiativeCode.getValues().contains(code)) {
      throw new InvalidInitiativeCodeException();
    }
  }
}
