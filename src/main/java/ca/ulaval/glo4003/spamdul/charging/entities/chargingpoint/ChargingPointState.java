package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

public interface ChargingPointState {

  void activate();

  void connect();

  void disconnect();

  long deactivate();
}
