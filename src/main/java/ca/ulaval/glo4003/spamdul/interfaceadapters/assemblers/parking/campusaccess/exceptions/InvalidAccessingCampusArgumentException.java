package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.exceptions;

public abstract class InvalidAccessingCampusArgumentException extends RuntimeException {

  public InvalidAccessingCampusArgumentException(String message) {
    super(message);
  }
}
