package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint.exceptions;

public class ChargingPointNotDisconnectedException extends ChargingPointException {

  @Override public String getError() {
    return "CHARGING_POINT_NOT_DISCONNECTED";
  }

  @Override public String getDescription() {
    return "The charging point must be disconnected first";
  }
}
