package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.transactions.TransactionFactory;

public class RechargULCardFactory {

  private final TransactionFactory transactionFactory;

  public RechargULCardFactory(TransactionFactory transactionFactory) {
    this.transactionFactory = transactionFactory;
  }

  public RechargULCard create() {
    return new RechargULCard(new RechargULCardId(), transactionFactory);
  }
}
