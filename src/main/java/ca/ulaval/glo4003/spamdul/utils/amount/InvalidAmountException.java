package ca.ulaval.glo4003.spamdul.utils.amount;

public class InvalidAmountException extends RuntimeException {

  public InvalidAmountException() {
    super("The given amount is invalid");
  }
}
