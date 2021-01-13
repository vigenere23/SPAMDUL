package ca.ulaval.glo4003.spamdul.shared.usecases.exceptions;

public class ItemNotFoundException extends UseCaseException {

  public ItemNotFoundException(RuntimeException exception) {
    super(exception);
  }
}
