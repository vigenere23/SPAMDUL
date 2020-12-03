package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;

public class ChargingPointActivator {
  private final ChargingPointRepository chargingPointRepository;

  public ChargingPointActivator(ChargingPointRepository chargingPointRepository) {
    this.chargingPointRepository = chargingPointRepository;
  }

  public void activate(RechargULCard rechargULCard, ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    chargingPoint.activate(rechargULCard);
    chargingPointRepository.update(chargingPoint);
  }
}
