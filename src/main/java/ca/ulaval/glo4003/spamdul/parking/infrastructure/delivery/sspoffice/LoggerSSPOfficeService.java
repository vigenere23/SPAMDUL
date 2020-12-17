package ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.sspoffice;

import ca.ulaval.glo4003.spamdul.parking.entities.delivery.DeliveryOptions;
import ca.ulaval.glo4003.spamdul.parking.entities.delivery.sspoffice.SSPOfficeDeliverer;
import ca.ulaval.glo4003.spamdul.parking.infrastructure.delivery.post.LoggerPostalService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerSSPOfficeService implements SSPOfficeDeliverer {

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
