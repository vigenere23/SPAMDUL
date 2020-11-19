package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;

public class ChargingPointService {

  private final ChargingPointRepository chargingPointRepository;
  private final RechargULCardRepository rechargULCardRepository;

  public ChargingPointService(ChargingPointRepository chargingPointRepository,
                              RechargULCardRepository rechargULCardRepository) {
    this.chargingPointRepository = chargingPointRepository;
    this.rechargULCardRepository = rechargULCardRepository;
  }

  public void activateChargingPoint(String chargingPointIdString, String rechargULCardIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);

    chargingPoint.activate(rechargULCard);

    chargingPointRepository.update(chargingPoint);
  }

  public void startRecharging(String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.connect();

    chargingPointRepository.update(chargingPoint);
  }

  public void stopRecharging(String chargingPointIdString) {
    ChargingPointId chargingPointId = ChargingPointId.valueOf(chargingPointIdString);
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.disconnect();

    chargingPointRepository.update(chargingPoint);
  }
}
