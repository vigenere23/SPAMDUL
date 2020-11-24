package ca.ulaval.glo4003.spamdul.entity.finance;

import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
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
