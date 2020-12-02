package ca.ulaval.glo4003.spamdul.context.charging;

import ca.ulaval.glo4003.spamdul.context.Populator;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPaymentService;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointFactory;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.concurrent.TimeUnit;

public class ChargingPointPopulator implements Populator {

  private final ChargingPointFactory chargingPointFactory;
  private final ChargingPointRepository chargingPointRepository;

  public ChargingPointPopulator(ChargingPointFactory chargingPointFactory,
                                ChargingPointRepository chargingPointRepository) {

    this.chargingPointFactory = chargingPointFactory;
    this.chargingPointRepository = chargingPointRepository;
  }

  @Override public void populate(int numberOfRecords) {
    ChargingPaymentService chargingPaymentService = new ChargingPaymentService(Amount.valueOf(1), TimeUnit.HOURS);

    for (int chargingPointNumber = 0; chargingPointNumber < numberOfRecords; chargingPointNumber++) {
      ChargingPoint chargingPoint = chargingPointFactory.create(chargingPaymentService);
      chargingPointRepository.save(chargingPoint);
    }
  }
}
