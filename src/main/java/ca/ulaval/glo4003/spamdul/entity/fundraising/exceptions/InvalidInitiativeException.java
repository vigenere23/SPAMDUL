package ca.ulaval.glo4003.spamdul.entity.fundraising.exceptions;

public abstract class InvalidInitiativeException extends RuntimeException {

  protected InvalidInitiativeException(String message) {
    super(message);
  }
}
