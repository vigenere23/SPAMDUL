package ca.ulaval.glo4003.spamdul.entity.charging_point;

public interface ChargingPointRepository {

  ChargingPoint findBy(ChargingPointId id);

  void save(ChargingPoint chargingPoint);

  void update(ChargingPoint chargingPoint);
}
