package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.ArrayList;
import java.util.List;

public class MainBankAccount {

  //TODO a tester au complet

  private final Account other;
  private final Account sustainableMobilityProjectAccount;
  private final double sustainableMobilityProjectRatio;

  public MainBankAccount(Account sustainableMobilityProjectAccount, Account other, double sustainableMobilityProjectRatio) {
    this.sustainableMobilityProjectAccount = sustainableMobilityProjectAccount;
    this.other = other;
    this.sustainableMobilityProjectRatio = sustainableMobilityProjectRatio;
  }

  public void addTransaction(Transaction transaction) {
    Amount sustainabilityAmount = transaction.getAmount().multiply(sustainableMobilityProjectRatio);
    Amount otherAmount = transaction.getAmount().multiply(1 - sustainableMobilityProjectRatio);

    Transaction sustainabilityTransaction = new Transaction(transaction, sustainabilityAmount);
    Transaction otherTransaction = new Transaction(transaction, otherAmount);

    sustainableMobilityProjectAccount.addTransaction(sustainabilityTransaction);
    other.addTransaction(otherTransaction);
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
