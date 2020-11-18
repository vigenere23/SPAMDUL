package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardCredits;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardDebiting;
import ca.ulaval.glo4003.spamdul.entity.transactions.Transaction;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class RechargULCard {

  private final RechargULCardId id;
  private final TransactionFactory transactionFactory;

  private final List<Transaction> transactions = new ArrayList<>();

  public RechargULCard(RechargULCardId id,
                       TransactionFactory transactionFactory) {
    this.id = id;
    this.transactionFactory = transactionFactory;
  }

  public void debit(Amount amount) {
    if (amount.isNegative() || amount.isZero()) {
      throw new InvalidRechargULCardDebiting();
    }

    Transaction transaction = transactionFactory.create(TransactionType.RECHARGE, amount.multiply(-1));
    transactions.add(transaction);
  }

  public void addCredits(Amount amount) {
    if (amount.isNegative() || amount.isZero()) {
      throw new InvalidRechargULCardCredits();
    }

    Transaction transaction = transactionFactory.create(TransactionType.RECHARGE_CREDITS, amount);
    transactions.add(transaction);
  }

  public boolean hasEnoughCredits() {
    return total().isNegative() || total().isZero();
  }

  public RechargULCardId getId() {
    return id;
  }

  public Amount total() {
    return transactions.stream()
                       .map(Transaction::getAmount)
                       .reduce(Amount::add)
                       .orElse(Amount.valueOf(0));
  }
}
