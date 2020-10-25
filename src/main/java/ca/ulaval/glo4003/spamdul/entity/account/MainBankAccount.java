package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.ArrayList;
import java.util.List;

public class MainBankAccount {

  private final Account other;
  private final Account sustainableMobilityProjectAccount;

  public MainBankAccount(Account sustainableMobilityProjectAccount, Account other) {
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
    this.other = other;
  }

  public void addTransaction(Transaction transaction) {
    sustainableMobilityProjectAccount.addTransaction(transaction);
    other.addTransaction(transaction);
  }

  public Amount getTotalInitiativesBudget() {
    return sustainableMobilityProjectAccount.getTotalAvailableAmount();
  }

  public List<Transaction> findAllBy(TransactionType transactionType) {
    List<Transaction> transactions;

    if (transactionType.equals(TransactionType.INFRACTION)) {
      transactions = sustainableMobilityProjectAccount.findAllTransactionsBy(transactionType);
    } else {
      transactions = new ArrayList<>();
      transactions.addAll(sustainableMobilityProjectAccount.findAllTransactionsBy(transactionType));
      transactions.addAll(other.findAllTransactionsBy(transactionType));
    }

    return transactions;
  }
}
