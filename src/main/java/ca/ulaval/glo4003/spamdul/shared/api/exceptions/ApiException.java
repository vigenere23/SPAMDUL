package ca.ulaval.glo4003.spamdul.shared.api.exceptions;

import ca.ulaval.glo4003.spamdul.shared.usecases.exceptions.UseCaseException;

public abstract class ApiException extends RuntimeException {

  private final String name;
  private final String code;

  protected ApiException(String name, String description) {
    this(name, null, description);
  }

  protected ApiException(String name, String code, String description) {
    super(description);
    this.name = name;
    this.code = code;
  }

  protected ApiException(UseCaseException exception) {
    this(exception.getName(), exception.getCode(), exception.getMessage());
  }

  protected ApiException(Exception exception) {
    this(exception.getClass().getSimpleName(), exception.getMessage());
  }

  public abstract int getStatus();

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
