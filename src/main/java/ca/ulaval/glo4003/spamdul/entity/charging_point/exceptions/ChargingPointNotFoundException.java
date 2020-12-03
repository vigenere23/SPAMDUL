package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointNotFoundException extends ChargingPointException {

  public ChargingPointNotFoundException() {
    super("this charging point does not exist");
  }
}
