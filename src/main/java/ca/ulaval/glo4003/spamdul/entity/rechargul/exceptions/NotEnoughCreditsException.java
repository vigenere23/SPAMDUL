package ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions;

public class NotEnoughCreditsException extends RechargULException {

  public NotEnoughCreditsException() {
    super("This recharUL card does not have enough credits");
  }
}
