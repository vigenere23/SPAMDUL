package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULExceptionMapper;
import java.util.Set;

public class DevChargingContext extends ChargingContext {

  private static final int NUMBER_OF_CHARGING_POINTS = 10;
  private static final int NUMBER_OF_CARDS = 10;

  public DevChargingContext(TransactionFactory transactionFactory) {
    super(transactionFactory);
  }

  @Override protected void populateChargingPoints() {
    new ChargingPointPopulator(chargingPointFactory, chargingPointRepository).populate(NUMBER_OF_CHARGING_POINTS);
  }

  @Override protected void populateRechargULCards() {
    new RechargULCardPopulator(rechargULCardFactory, rechargULCardRepository).populate(NUMBER_OF_CARDS);
  }

  @Override public void registerResources(Set<Object> resources) {
    resources.add(chargingPointResource);
    resources.add(new ChargingPointExceptionMapper());
    resources.add(rechargULResource);
    resources.add(new RechargULExceptionMapper());
  }
}
