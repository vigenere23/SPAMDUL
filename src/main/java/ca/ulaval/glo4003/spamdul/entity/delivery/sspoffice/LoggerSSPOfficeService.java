package ca.ulaval.glo4003.spamdul.entity.delivery.sspoffice;

import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.entity.delivery.DeliveryStrategy;
import ca.ulaval.glo4003.spamdul.entity.delivery.post.PostalAddress;
import ca.ulaval.glo4003.spamdul.infrastructure.delivery.post.LoggerPostalService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerSSPOfficeService implements DeliveryStrategy {

  private static final Logger LOGGER = Logger.getLogger(LoggerPostalService.class.getName());

  @Override
  public void deliver(DeliveryOptions deliveryOptions, String content) {
    send(content);
  }

  private void send(String content) {
    LOGGER.log(Level.INFO,
               String.format("ParkingPass %s will be picked up by the buyer at the ssp office",
                             content));
  }
}
