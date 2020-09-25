package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions;

public class InvalidCampusAccessArgumentException extends RuntimeException{
  public InvalidCampusAccessArgumentException(String message) {
    super(message);
  }
}
