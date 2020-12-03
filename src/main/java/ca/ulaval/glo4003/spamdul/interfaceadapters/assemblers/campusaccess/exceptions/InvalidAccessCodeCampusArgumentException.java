package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions;

public class InvalidAccessCodeCampusArgumentException extends InvalidAccessingCampusArgumentException{

  public InvalidAccessCodeCampusArgumentException() {
    super("A campus access code or a license plate must be provided");
  }
}
