package ca.ulaval.glo4003.spamdul.entity.charging_point;

public interface ChargingPointState {

  void activate();

  void connect();

  void disconnect();

  long deactivate();
}
