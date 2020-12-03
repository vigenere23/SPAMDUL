package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.exceptions;

public abstract class InvalidPassArgumentException extends RuntimeException {

  protected InvalidPassArgumentException(String message) {
    super(message);
  }
}
