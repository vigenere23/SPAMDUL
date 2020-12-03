package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions;

public class InvalidCampusAccessCodeException extends InvalidPassArgumentException {

  public InvalidCampusAccessCodeException() {
    super("The campus access code is not in the right format");
  }
}
