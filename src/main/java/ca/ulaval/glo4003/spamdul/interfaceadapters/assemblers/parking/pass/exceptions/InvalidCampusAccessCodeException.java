package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.exceptions;

public class InvalidCampusAccessCodeException extends InvalidPassArgumentException {

  public String getError() {
    return "INVALID_CAMPUS_ACCESS_CODE";
  }

  public String getDescription() {
    return "The campus access code is not in the right format";
  }
}
