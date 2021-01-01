package ca.ulaval.glo4003.spamdul.charging.context;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

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
