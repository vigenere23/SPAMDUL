package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardRepository;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import java.util.List;

public class ChargingUseCase {

  private final ChargingPointRepository chargingPointRepository;
  private final RechargULCardRepository rechargULCardRepository;

  public ChargingUseCase(ChargingPointRepository chargingPointRepository,
                         RechargULCardRepository rechargULCardRepository) {
    this.chargingPointRepository = chargingPointRepository;
    this.rechargULCardRepository = rechargULCardRepository;
  }

  public List<ChargingPoint> getAllChargingPoints() {
    return chargingPointRepository.findAll();
  }

  public ChargingPoint activateChargingPoint(ChargingPointId chargingPointId, RechargULCardId rechargULCardId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);

    chargingPoint.activate(rechargULCard);

    chargingPointRepository.update(chargingPoint);
    return chargingPoint;
  }

  public ChargingPoint startRecharging(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    chargingPoint.connect();
    chargingPointRepository.update(chargingPoint);
    return chargingPoint;
  }

  public ChargingPoint stopRecharging(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    chargingPoint.disconnect();
    chargingPointRepository.update(chargingPoint);
    return chargingPoint;
  }

  public ChargingPoint deactivateChargingPoint(ChargingPointId chargingPointId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    chargingPoint.deactivate();
    chargingPointRepository.update(chargingPoint);
    return chargingPoint;
  }

  public RechargULCard getRechargeULCard(RechargULCardId rechargULCardId) {
    return rechargULCardRepository.findBy(rechargULCardId);
  }

  public RechargULCard addCredits(RechargULCardId rechargULCardId, Amount amount) {
    RechargULCard rechargULCard = rechargULCardRepository.findBy(rechargULCardId);

    rechargULCard.addCredits(amount);

    rechargULCardRepository.update(rechargULCard);
    return rechargULCard;
  }
}
