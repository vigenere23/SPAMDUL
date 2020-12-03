package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.exceptions;

import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.RechargULException;

public class InvalidRechargCardUlUserIdFormatException extends RechargULException {

  public InvalidRechargCardUlUserIdFormatException() {
    super("Invalid rechargUL card request. Invalid user id format");
  }
}
