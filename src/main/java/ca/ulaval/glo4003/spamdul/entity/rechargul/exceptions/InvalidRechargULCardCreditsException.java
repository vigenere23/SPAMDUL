package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class InvalidRechargULCardCreditsException extends RechargULException {

  public String getError() {
    return "INVALID_RECHARGUL_CREDITS";
  }

  public String getDescription() {
    return "Invalid credits for rechargUL card";
  }
}
