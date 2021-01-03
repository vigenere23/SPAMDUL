package ca.ulaval.glo4003.spamdul.parking2.entities.access;

public class InvalidAccess extends RuntimeException {

  public InvalidAccess() {
    super("Invalid access");
  }
}
