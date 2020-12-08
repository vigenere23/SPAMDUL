package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions;

public class InvalidCampusAccessArgumentException extends RuntimeException {

  public InvalidCampusAccessArgumentException() {
    super("A campus access code or a license plate must be provided");
  }
}
