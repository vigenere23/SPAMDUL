package ca.ulaval.glo4003.spamdul.entity.finance.bank_accounts;

import ca.ulaval.glo4003.spamdul.entity.finance.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionList;
import ca.ulaval.glo4003.spamdul.entity.finance.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class InitiativesBankAccount {

  private final TransactionType TRANSACTION_TYPE = TransactionType.INITIATIVE;

  private final SustainabilityBankAccount sustainabilityBankAccount;

  public InitiativesBankAccount(SustainabilityBankAccount sustainabilityBankAccount) {
    this.sustainabilityBankAccount = sustainabilityBankAccount;
  }

  public void addExpense(Amount amount) {
    sustainabilityBankAccount.addExpense(amount, TRANSACTION_TYPE);
  }

  public Amount getBalance() {
    List<Transaction> transactions = sustainabilityBankAccount.getTransactionsOfType(TRANSACTION_TYPE);
    TransactionList transactionList = new TransactionList(transactions);
    return transactionList.getBalance();
  }
}
