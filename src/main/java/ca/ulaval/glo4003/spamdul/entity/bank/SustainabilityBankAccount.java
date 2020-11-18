package ca.ulaval.glo4003.spamdul.entity.bank;

import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class SustainabilityBankAccount {

  private final Map<TransactionType, List<Transaction>> transactionsByType;

  public SustainabilityBankAccount() {
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
