package ca.ulaval.glo4003.spamdul.charging.entities.rechargul.exceptions;

public class NotEnoughCreditsException extends RechargULException {

  @Override public String getError() {
    return "NOT_ENOUGH_CREDITS";
  }

  @Override public String getDescription() {
    return "This rechargUL card does not have enough credits";
  }
}
