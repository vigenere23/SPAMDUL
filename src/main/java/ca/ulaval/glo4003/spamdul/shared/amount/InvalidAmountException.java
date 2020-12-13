package ca.ulaval.glo4003.spamdul.shared.amount;

public class InvalidAmountException extends RuntimeException {

  public InvalidAmountException() {
    super("Invalid amount");
  }
}
