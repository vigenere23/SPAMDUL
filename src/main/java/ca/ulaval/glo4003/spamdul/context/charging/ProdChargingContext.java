package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import java.util.Set;

public class ProdChargingContext extends ChargingContext {

  public ProdChargingContext(TransactionFactory transactionFactory) {
    super(transactionFactory);
  }

  @Override protected void populateChargingPoints() {

  }

  @Override protected void populateRechargULCards() {

  }

  @Override public void registerResources(Set<Object> resources) {

  }
}
