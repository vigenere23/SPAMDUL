package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardCreditsException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardDebitingException;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import java.util.ArrayList;
import java.util.List;

public class RechargULCard {

  private final RechargULCardId id;
  private final TransactionFactory transactionFactory;

  private final List<Transaction> transactions = new ArrayList<>();

  public RechargULCard(RechargULCardId id, TransactionFactory transactionFactory) {
    this.id = id;
    this.transactionFactory = transactionFactory;
  }

  public void debit(Amount amount) {
    if (amount.isStrictlyNegative() || amount.isZero()) {
      throw new InvalidRechargULCardDebitingException();
    }

    Transaction transaction = transactionFactory.create(TransactionType.RECHARGE, amount.multiply(-1));
    transactions.add(transaction);
  }

  public void addCredits(Amount amount) {
    if (amount.isStrictlyNegative() || amount.isZero()) {
      throw new InvalidRechargULCardCreditsException();
    }

    Transaction transaction = transactionFactory.create(TransactionType.RECHARGE_CREDITS, amount);
    transactions.add(transaction);
  }

  public void verifyEnoughCreditsForCharging() {
    if (!total().isStrictlyPositive()) {
      throw new NotEnoughCreditsException();
    }
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
