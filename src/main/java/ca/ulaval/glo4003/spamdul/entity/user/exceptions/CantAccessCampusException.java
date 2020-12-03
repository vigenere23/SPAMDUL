package ca.ulaval.glo4003.spamdul.entity.user.exceptions;

public class CantAccessCampusException extends RuntimeException {

  public CantAccessCampusException() {
    super("User can't access campus");
  }
}
