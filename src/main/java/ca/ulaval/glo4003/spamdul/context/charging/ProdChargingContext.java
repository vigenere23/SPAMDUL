package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;

public class ProdChargingContext extends ChargingContext {

  public ProdChargingContext(TransactionFactory transactionFactory) {
    super(transactionFactory);
  }

  @Override protected void populateChargingPoints(Populator populator) {

  }

  @Override protected void populateRechargULCards(Populator populator) {

  }
}
