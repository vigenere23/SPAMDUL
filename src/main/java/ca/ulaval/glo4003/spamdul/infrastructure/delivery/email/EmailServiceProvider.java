package ca.ulaval.glo4003.spamdul.infrastructure.delivery.email;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;

public class EmailServiceProvider {

  private final String mode;

  public EmailServiceProvider(String mode) {
    if (mode == null) {
      mode = "";
    }

    this.mode = mode;
  }

  public DeliveryStrategy provide() {
    if (mode.equals("ci")) {
      return new NullEmailService();
    } else {
      return new GmailEmailService();
    }
  }
}
