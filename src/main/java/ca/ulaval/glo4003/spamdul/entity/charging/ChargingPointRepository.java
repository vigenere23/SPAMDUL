package ca.ulaval.glo4003.spamdul.entity.charging;

import java.util.List;

public interface ChargingPointRepository {

  List<ChargingPoint> findAll();

  ChargingPoint findBy(ChargingPointId id);

  void save(ChargingPoint chargingPoint);

  void update(ChargingPoint chargingPoint);
}
