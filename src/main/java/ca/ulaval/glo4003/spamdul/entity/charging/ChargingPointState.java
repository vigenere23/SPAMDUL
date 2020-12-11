package ca.ulaval.glo4003.spamdul.entity.charging;

public interface ChargingPointState {

  void activate();

  void connect();

  void disconnect();

  long deactivate();
}
