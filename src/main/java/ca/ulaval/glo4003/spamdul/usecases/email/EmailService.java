package ca.ulaval.glo4003.spamdul.usecases.email;

public interface EmailService {

  void send(String to, String subject, String text);
}
