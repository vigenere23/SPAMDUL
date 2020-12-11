package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging.ChargingPointRepository;

public class ChargingPointPopulator implements Populator {

  private final ChargingPointFactory chargingPointFactory;
  private final ChargingPointRepository chargingPointRepository;

  public ChargingPointPopulator(ChargingPointFactory chargingPointFactory,
                                ChargingPointRepository chargingPointRepository) {

    this.chargingPointFactory = chargingPointFactory;
    this.chargingPointRepository = chargingPointRepository;
  }

  @Override public void populate(int numberOfRecords) {
    for (int chargingPointNumber = 0; chargingPointNumber < numberOfRecords; chargingPointNumber++) {
      ChargingPoint chargingPoint = chargingPointFactory.create();
      chargingPointRepository.save(chargingPoint);
    }
  }
}
