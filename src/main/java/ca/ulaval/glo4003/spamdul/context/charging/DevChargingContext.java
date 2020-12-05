package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class DevChargingContext extends ChargingContext {

  private static final int NUMBER_OF_CHARGING_POINTS = 10;
  private static final int NUMBER_OF_CARDS = 10;

  public DevChargingContext(TransactionFactory transactionFactory, UserRepository userRepository) {
    super(transactionFactory, userRepository);
  }

  @Override protected void populateChargingPoints(Populator populator) {
    populator.populate(NUMBER_OF_CHARGING_POINTS);
  }

  @Override protected void populateRechargULCards(Populator populator) {
    populator.populate(NUMBER_OF_CARDS);
  }
}
