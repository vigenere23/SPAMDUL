package ca.ulaval.glo4003.spamdul.finance.entities.transaction;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;

public class TransactionList {

  private final List<Transaction> transactions;

  public TransactionList(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Amount getBalance() {
    return transactions.stream().map(Transaction::getAmount).reduce(Amount::add).orElse(Amount.valueOf(0));
  }
}
