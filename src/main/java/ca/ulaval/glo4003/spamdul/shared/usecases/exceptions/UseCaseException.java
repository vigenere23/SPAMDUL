package ca.ulaval.glo4003.spamdul.shared.usecases.exceptions;

public abstract class UseCaseException extends RuntimeException {

  private final String name;
  private final String code;

  protected UseCaseException(String name, String code, String message) {
    super(message);
    this.name = name;
    this.code = code;
  }

  protected UseCaseException(String name, String message) {
    this(name, null, message);
  }

  protected UseCaseException(Exception exception) {
    this(exception.getClass().getSimpleName(), exception.getMessage());
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
