package ca.ulaval.glo4003.spamdul.entity.delivery;

import ca.ulaval.glo4003.spamdul.entity.delivery.email.EmailServiceProvider;
import ca.ulaval.glo4003.spamdul.entity.delivery.sspoffice.LoggerSSPOfficeService;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;

public class DeliveryStrategyFactory {

  private final EmailServiceProvider emailServiceProvider;

  public DeliveryStrategyFactory(EmailServiceProvider emailServiceProvider) {
    this.emailServiceProvider = emailServiceProvider;
  }

  public DeliveryStrategy create(DeliveryMode deliveryMode) {
    if (deliveryMode == DeliveryMode.EMAIL) {
      return emailServiceProvider.provide();

    } else if (deliveryMode == DeliveryMode.POST) {
      return new LoggerPostalService();

    } else if(deliveryMode == DeliveryMode.SSP_OFFICE) {
      return new LoggerSSPOfficeService();

    } else {
      throw new InvalidDeliveryModeException("Invalid delivery mode");
    }
  }
}
