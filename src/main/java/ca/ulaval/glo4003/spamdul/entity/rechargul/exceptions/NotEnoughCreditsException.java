package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class NotEnoughCreditsException extends RechargULException {

  public String getError() {
    return "NOT_ENOUGH_CREDITS";
  }

  public String getDescription() {
    return "This rechargUL card does not have enough credits";
  }
}
