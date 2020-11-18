package ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions;

public class ChargingCounterAlreadyStartedException extends ChargingCounterException {

  public ChargingCounterAlreadyStartedException() {
    super("The charging counter has already started");
  }
}
