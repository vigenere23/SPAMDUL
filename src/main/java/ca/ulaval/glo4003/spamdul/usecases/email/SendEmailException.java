package ca.ulaval.glo4003.spamdul.usecases.email;

public class SendEmailException extends RuntimeException {

  public SendEmailException() {
    super("Fail to send email");
  }
}
