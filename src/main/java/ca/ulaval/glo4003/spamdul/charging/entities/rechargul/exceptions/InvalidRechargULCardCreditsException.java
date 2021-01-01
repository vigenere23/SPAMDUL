package ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions;

public class InvalidRechargULCardCreditsException extends RechargULException {

  @Override public String getError() {
    return "INVALID_RECHARGUL_CREDITS";
  }

  @Override public String getDescription() {
    return "Invalid credits for rechargUL card";
  }
}
