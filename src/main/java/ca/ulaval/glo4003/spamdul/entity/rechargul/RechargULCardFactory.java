package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;

public class RechargULCardFactory {

  private final RechargULCardIdFactory rechargULCardIdFactory;
  private final TransactionFactory transactionFactory;

  public RechargULCardFactory(RechargULCardIdFactory rechargULCardIdFactory,
                              TransactionFactory transactionFactory) {
    this.rechargULCardIdFactory = rechargULCardIdFactory;
    this.transactionFactory = transactionFactory;
  }

  public RechargULCard create() {
    return new RechargULCard(rechargULCardIdFactory.create(), transactionFactory);
  }
}
