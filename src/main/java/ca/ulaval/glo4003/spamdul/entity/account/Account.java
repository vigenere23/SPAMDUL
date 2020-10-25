package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Account {

  private final double ratio;
  private final Map<TransactionType, List<Transaction>> transactionsByType;

  public Account(double ratio) {
    this.ratio = ratio;
    this.transactionsByType = new EnumMap<>(TransactionType.class);
  }

  public void addTransaction(Transaction transaction) {
    Amount total = getTotalAvailableAmount();
    Amount newTransactionAmount = transaction.getAmount().multiply(ratio);

    if (total.asDouble() < newTransactionAmount.asDouble()) {
      throw new InsufficientFundsException("Insufficient funds");
    }

    Transaction newTransaction = new Transaction(transaction, newTransactionAmount);
    List<Transaction> transactions = transactionsByType.get(transaction.getTransactionType());

    if (transactions == null) {
      transactions = new ArrayList<>();
    }

    transactions.add(newTransaction);
  }

  public Amount getTotalAvailableAmount() {
    Amount amount = Amount.valueOf(0);

    for (Entry<TransactionType, List<Transaction>> entry : transactionsByType.entrySet()) {
      for (Transaction transaction : entry.getValue()) {
        amount = amount.add(transaction.getAmount());
      }
    }

    return amount;
  }

  public List<Transaction> findAllTransactionsBy(TransactionType transactionType) {
    return transactionsByType.get(transactionType);
  }
}
