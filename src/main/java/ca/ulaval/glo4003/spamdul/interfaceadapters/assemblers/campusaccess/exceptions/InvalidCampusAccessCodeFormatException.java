package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.exceptions;

public class InvalidCampusAccessCodeFormatException extends InvalidAccessingCampusArgumentException {

  public InvalidCampusAccessCodeFormatException() {
    super("The access campus code is not in the right format");
  }
}
