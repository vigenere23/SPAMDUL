package ca.ulaval.glo4003.spamdul.entity.charging_point;

public interface ChargingPointRepository {

  ChargingPoint find(ChargingPointId id);

  void save(ChargingPoint chargingPoint);

  void update(ChargingPoint chargingPoint);
}
