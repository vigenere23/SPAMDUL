package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.charging_point.*;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;

import java.util.List;

public class ChargingPointService {

  private final ChargingPointRepository chargingPointRepository;
  private final EnoughCreditForChargingVerifier enoughCreditForChargingVerifier;
  private final ChargingPaymentService chargingPaymentService;

  public ChargingPointService(ChargingPointRepository chargingPointRepository,
                              EnoughCreditForChargingVerifier enoughCreditForChargingVerifier,
                              ChargingPaymentService chargingPaymentService) {
    this.chargingPointRepository = chargingPointRepository;
    this.enoughCreditForChargingVerifier = enoughCreditForChargingVerifier;
    this.chargingPaymentService = chargingPaymentService;
  }


  public List<ChargingPoint> getAllChargingPoints() {
    return chargingPointRepository.findAll();
  }

  public ChargingPoint getChargingPoint(ChargingPointId chargingPointId) {
    return chargingPointRepository.findBy(chargingPointId);
  }

  public ChargingPoint activateChargingPoint(ChargingPointId chargingPointId, RechargULCardId rechargULCardId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);

    chargingPoint.activate(enoughCreditForChargingVerifier, rechargULCardId);

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

    chargingPoint.deactivateAndPay(chargingPaymentService);
    
    chargingPointRepository.update(chargingPoint);
    return chargingPoint;
  }
}
