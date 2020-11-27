package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public class InitiativeBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.INITIATIVE;

  private final SustainabilityBankAccount sustainabilityBankAccount;

  public InitiativeBankAccount(SustainabilityBankAccount sustainabilityBankAccount) {
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public void addExpense(Amount amount) {
    sustainabilityBankAccount.addExpense(amount, TRANSACTION_TYPE);
  }
}
