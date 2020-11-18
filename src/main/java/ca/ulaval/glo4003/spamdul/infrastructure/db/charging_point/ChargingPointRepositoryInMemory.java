package ca.ulaval.glo4003.spamdul.infrastructure.db.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPoint;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointId;
import ca.ulaval.glo4003.spamdul.entity.charging_point.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointAlreadyExistsException;
import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.ChargingPointNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class ChargingPointRepositoryInMemory implements ChargingPointRepository {

  private final Map<ChargingPointId, ChargingPoint> chargingPoints = new HashMap<>();

  @Override public List<ChargingPoint> findAll() {
    return Lists.newArrayList(chargingPoints.values());
  }

  @Override public ChargingPoint findBy(ChargingPointId id) {
    if (!chargingPoints.containsKey(id)) {
      throw new ChargingPointNotFoundException();
    }

    return chargingPoints.get(id);
  }

  @Override public void save(ChargingPoint chargingPoint) {
    if (chargingPoints.containsKey(chargingPoint.getId())) {
      throw new ChargingPointAlreadyExistsException();
    }
    
    chargingPoints.put(chargingPoint.getId(), chargingPoint);
  }

  @Override public void update(ChargingPoint chargingPoint) {
    if (!chargingPoints.containsKey(chargingPoint.getId())) {
      throw new ChargingPointNotFoundException();
    }

    chargingPoints.put(chargingPoint.getId(), chargingPoint);
  }
}
