package ca.ulaval.glo4003.spamdul.finance.entities.transaction_services;

import ca.ulaval.glo4003.spamdul.finance.entities.bank_accounts.SustainabilityBankAccount;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public class InitiativeTransactionService {

  private final TransactionType TRANSACTION_TYPE = TransactionType.INITIATIVE;

  private final SustainabilityBankAccount sustainabilityBankAccount;

  public InitiativeTransactionService(SustainabilityBankAccount sustainabilityBankAccount) {
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public void addExpense(Amount amount) {
    sustainabilityBankAccount.addExpense(amount, TRANSACTION_TYPE);
  }
}
