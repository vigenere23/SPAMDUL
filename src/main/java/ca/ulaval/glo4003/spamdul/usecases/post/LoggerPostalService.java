package ca.ulaval.glo4003.spamdul.usecases.post;

import ca.ulaval.glo4003.spamdul.entity.user.UserAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPostalService implements PostalService {

  private static final Logger LOGGER = Logger.getLogger(LoggerPostalService.class.getName());

  public void send(String recipientName, UserAddress recipientAddress, String content) {
    LOGGER.log(Level.INFO,
               String.format("Sending parcel to %s at address %s with content %s",
                             recipientName,
                             recipientAddress,
                             content));
  }
}
