package ca.ulaval.glo4003.spamdul.entity.delivery.email;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;

public class EmailServiceFactory {

  private final DeliveryStrategy emailService;

  public EmailServiceFactory(DeliveryStrategy emailService) {
    this.emailService = emailService;
  }

  public DeliveryStrategy create() {
    return emailService;
  }
}
