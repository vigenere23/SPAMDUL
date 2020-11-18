package ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;

public class ChargingPointRepositoryInMemory implements ChargingPointRepository {

  @Override public ChargingPoint findBy(ChargingPointId id) {
    return null;
  }

  @Override public void save(ChargingPoint chargingPoint) {

  }

  @Override public void update(ChargingPoint chargingPoint) {

  }
}
