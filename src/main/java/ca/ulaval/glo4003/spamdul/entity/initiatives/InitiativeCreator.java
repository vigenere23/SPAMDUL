package ca.ulaval.glo4003.spamdul.entity.initiatives;

import ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts.InitiativesBankAccount;
import ca.ulaval.glo4003.spamdul.entity.finance.exceptions.InsufficientFundsException;
import ca.ulaval.glo4003.spamdul.entity.initiatives.exceptions.InvalidInitiativeAmount;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeCreator {

  private final InitiativesBankAccount initiativesBankAccount;
  private final InitiativeFactory initiativeFactory;

  public InitiativeCreator(InitiativesBankAccount initiativesBankAccount,
                           InitiativeFactory initiativeFactory) {
    this.initiativesBankAccount = initiativesBankAccount;
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative createInitiative(InitiativeCode code, String name, Amount amount) {
    try {
      initiativesBankAccount.addExpense(amount);
    } catch (InsufficientFundsException e) {
      throw new InvalidInitiativeAmount("Insufficient funds");
    }

    return initiativeFactory.create(code, name, amount);
  }
}
