package ca.ulaval.glo4003.spamdul.usecases.charging;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULCardNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import java.util.List;

public class ChargingPointService {

  private final ChargingPointRepository chargingPointRepository;
  private final UserRepository userRepository;

  public ChargingPointService(ChargingPointRepository chargingPointRepository,
                              UserRepository userRepository) {
    this.chargingPointRepository = chargingPointRepository;
    this.userRepository = userRepository;
  }

  public List<ChargingPoint> getAllChargingPoints() {
    return chargingPointRepository.findAll();
  }

  public ChargingPoint getChargingPoint(ChargingPointId chargingPointId) {
    return chargingPointRepository.findBy(chargingPointId);
  }

  public ChargingPoint activateChargingPoint(ChargingPointId chargingPointId, RechargULCardId rechargULCardId) {
    ChargingPoint chargingPoint = chargingPointRepository.findBy(chargingPointId);
    try {
      RechargULCard rechargULCard = userRepository.findBy(rechargULCardId).getRechargUlCard();

      chargingPoint.activate(rechargULCard);

      chargingPointRepository.update(chargingPoint);
    } catch (UserNotFoundException e) {
      throw new RechargULCardNotFoundException();
    }

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
}
