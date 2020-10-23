package ca.ulaval.glo4003.spamdul.infrastructure.delivery.post;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPostalService implements DeliveryStrategy {

  private static final Logger LOGGER = Logger.getLogger(LoggerPostalService.class.getName());

  @Override
  public void deliver(DeliveryOptions deliveryOptions, String content) {
    send(deliveryOptions.postalAddress, content);
  }

  private void send(PostalAddress recipientAddress, String content) {
    LOGGER.log(Level.INFO,
               String.format("Sending parcel at address:\n %s with content %s",
                             recipientAddress.toString(),
                             content));
  }


}
