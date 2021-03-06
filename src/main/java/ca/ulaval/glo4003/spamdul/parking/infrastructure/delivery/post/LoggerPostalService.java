package ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.post;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.post.PostalDeliverer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPostalService implements PostalDeliverer {

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
