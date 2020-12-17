package ca.ulaval.glo4003.spamdul.charging.infrastructure.persistence.chargingpoint;

import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPoint;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointId;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.ChargingPointRepository;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointAlreadyExistsException;
import ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions.ChargingPointNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jersey.repackaged.com.google.common.collect.Lists;

public class InMemoryChargingPointRepository implements ChargingPointRepository {

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
