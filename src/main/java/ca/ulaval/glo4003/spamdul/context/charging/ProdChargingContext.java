package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.car.CarFactory;

public class ProdChargingContext extends ChargingContext {

  public ProdChargingContext(TransactionFactory transactionFactory,
                             UserRepository userRepository,
                             CarFactory carFactory) {
    super(transactionFactory, userRepository, carFactory);
  }

  @Override protected void populateChargingPoints(Populator populator) {

  }

  @Override protected void populateRechargULCards(Populator populator) {

  }
}
