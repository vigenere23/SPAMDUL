package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardCreditsException extends RechargULException {

  public InvalidRechargULCardCreditsException() {
    super("Invalid credits for rechargUL card");
  }
}
