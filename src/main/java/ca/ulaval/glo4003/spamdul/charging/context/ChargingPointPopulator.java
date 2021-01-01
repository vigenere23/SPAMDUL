package ca.ulaval.glo4003.spamdul.charging.context;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPoint;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.shared.context.Populator;

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
