package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativeBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmountException;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeCreator {

  private final InitiativeBankAccount initiativeBankAccount;
  private final InitiativeFactory initiativeFactory;

  public InitiativeCreator(InitiativeBankAccount initiativeBankAccount,
                           InitiativeFactory initiativeFactory) {
    this.initiativeBankAccount = initiativeBankAccount;
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative createInitiative(String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(name, amount);
  }

  public Initiative createInitiative(InitiativeCode code, String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(code, name, amount);
  }

  public Initiative createInitiative(ReservedInitiativeCode reservedCode, String name, Amount amount) {
    addExpense(amount);

    return initiativeFactory.create(reservedCode, name, amount);
  }

  private void addExpense(Amount amount) {
    try {
      initiativeBankAccount.addExpense(amount);
    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmountException("Insufficient funds");
    }
  }
}
