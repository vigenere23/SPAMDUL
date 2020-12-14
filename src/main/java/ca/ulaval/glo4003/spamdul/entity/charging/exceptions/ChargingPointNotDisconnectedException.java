package ca.ulaval.glo4003.spamdul.entity.charging.exceptions;

public class ChargingPointNotDisconnectedException extends ChargingPointException {

  public String getError() {
    return "CHARGING_POINT_NOT_DISCONNECTED";
  }

  public String getDescription() {
    return "The charging point must be disconnected first";
  }
}
