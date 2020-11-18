package ca.ulaval.glo4003.spamdul.entity.charging_point;

import java.util.List;

public interface ChargingPointRepository {

  List<ChargingPoint> findAll();

  ChargingPoint find(ChargingPointId id);

  void save(ChargingPoint chargingPoint);

  void update(ChargingPoint chargingPoint);
}
