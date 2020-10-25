package ca.ulaval.glo4003.spamdul.infrastructure.delivery.email;

public class EmailException extends RuntimeException {

  public EmailException() {
    super("Fail to send email");
  }
}
