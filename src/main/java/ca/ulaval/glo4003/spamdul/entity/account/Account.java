package ca.ulaval.glo4003.spamdul.entity.account;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.Amount;

import java.util.*;
import java.util.Map.Entry;

public class Account {

  private final Map<TransactionType, List<Transaction>> transactionsByType;

  public Account() {
    this.transactionsByType = new EnumMap<>(TransactionType.class);
  }

  public void addTransaction(Transaction transaction) {
    Amount total = getTotalAvailableAmount();

    if (total.add(transaction.getAmount()).isNegative()) {
      throw new InsufficientFundsException("Insufficient funds");
    }

    List<Transaction> transactions = transactionsByType.get(transaction.getTransactionType());

    if (transactions == null) {
      transactions = new ArrayList<>();
      transactions.add(transaction);
      transactionsByType.put(transaction.getTransactionType(), transactions);
    } else {
      transactions.add(transaction);
    }
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
    return Optional.ofNullable(transactionsByType.get(transactionType)).orElse(new ArrayList<>());
  }
}
