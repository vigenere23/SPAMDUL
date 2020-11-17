package ca.ulaval.glo4003.spamdul.entity.charging_point;

public interface ChargingCounterRepository {

  ChargingCounter find(ChargingCounterId id);

  void save(ChargingCounter chargingCounter);

  void update(ChargingCounter chargingCounter);
}
