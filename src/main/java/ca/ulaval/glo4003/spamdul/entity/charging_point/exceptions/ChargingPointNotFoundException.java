package ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions;

public class ChargingPointNotFoundException extends ChargingPointException {

  public ChargingPointNotFoundException() {
    super("This charging point does not exist");
  }
}
