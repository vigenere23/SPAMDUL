package ca.ulaval.glo4003.spamdul.charging.context;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

public class DevChargingContext extends ChargingContext {

  private static final int NUMBER_OF_CHARGING_POINTS = 10;
  private static final int NUMBER_OF_CARDS = 10;

  public DevChargingContext(TransactionFactory transactionFactory,
                            UserRepository userRepository,
                            CarFactory carFactory) {
    super(transactionFactory, userRepository, carFactory);
  }

  @Override protected void populateChargingPoints(Populator populator) {
    populator.populate(NUMBER_OF_CHARGING_POINTS);
  }

  @Override protected void populateRechargULCards(Populator populator) {
    populator.populate(NUMBER_OF_CARDS);
  }
}
