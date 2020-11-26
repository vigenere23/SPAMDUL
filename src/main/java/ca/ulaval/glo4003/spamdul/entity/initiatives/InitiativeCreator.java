package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativeBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeCreator {

  private final InitiativeBankAccount initiativeBankAccount;
  private final InitiativeFactory initiativeFactory;

  public InitiativeCreator(InitiativeBankAccount initiativeBankAccount,
                           InitiativeFactory initiativeFactory) {
    this.initiativeBankAccount = initiativeBankAccount;
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative createInitiative(InitiativeCode code, String name, Amount amount) {
    try {
      initiativeBankAccount.addExpense(amount);
    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }

    return initiativeFactory.create(code, name, amount);
  }
}
