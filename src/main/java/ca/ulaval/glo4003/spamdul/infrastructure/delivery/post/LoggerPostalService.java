package ca.ulaval.glo4003.spamdul.infrastructure.delivery.post;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryBridge;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPostalService implements DeliveryBridge {

  private static final Logger LOGGER = Logger.getLogger(LoggerPostalService.class.getName());

  @Override
  public void send(DeliveryOptions deliveryOptions, String content) {
    send(deliveryOptions.recipientName, deliveryOptions.postalAddress, content);
  }

  private void send(String recipientName, PostalAddress recipientAddress, String content) {
    LOGGER.log(Level.INFO,
               String.format("Sending parcel to %s at address:\n %s with content %s",
                             recipientName,
                             recipientAddress.toString(),
                             content));
  }


}
