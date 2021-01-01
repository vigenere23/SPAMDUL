package ca.ulaval.glo4003.spamdul.charging.entities.rechargul;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.InvalidRechargULCardCreditsException;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.InvalidRechargULCardDebitingException;
import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions.NotEnoughCreditsException;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.Transaction;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionList;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
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
    return new TransactionList(transactions).getBalance();
  }
}
