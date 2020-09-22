package ca.ulaval.glo4003.projet.base.ws.domain.user;

public abstract class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }
}
