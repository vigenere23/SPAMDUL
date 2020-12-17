package ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.email;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.email.EmailDeliverer;
import java.util.logging.Logger;

public class NullEmailService implements EmailDeliverer {

  private static final Logger logger = Logger.getLogger(NullEmailService.class.getName());

  @Override
  public void deliver(DeliveryOptions deliveryOptions, String content) {
    logger.info(String.format("Emailing to %s:\n%s", deliveryOptions.emailAddress, content));
  }
}
