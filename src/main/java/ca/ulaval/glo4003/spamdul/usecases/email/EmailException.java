package ca.ulaval.glo4003.spamdul.usecases.email;

// TODO: refine this
public class EmailException extends RuntimeException {

  public EmailException() {
    super("Fail to send email");
  }
}
