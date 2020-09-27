package ca.ulaval.glo4003.spamdul.usecases.post;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPostalService implements PostalService {

  private static final Logger LOGGER = Logger.getLogger(LoggerPostalService.class.getName());

  public void send(String recipientName) {
    LOGGER.log(Level.INFO, "Sending parcel to {0}", recipientName);
  }
}
