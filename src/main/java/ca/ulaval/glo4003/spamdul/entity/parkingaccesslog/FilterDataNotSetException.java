package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

public class FilterDataNotSetException extends RuntimeException {

  public FilterDataNotSetException() {
    super("Cannot start filtering : data has not been set");
  }
}
