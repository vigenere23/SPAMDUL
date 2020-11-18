package ca.ulaval.glo4003.spamdul.entity.charging_counter.exceptions;

public class ChargingCounterNotStartedException extends ChargingCounterException {

  public ChargingCounterNotStartedException() {
    super("The charging counter must be started first");
  }
}
