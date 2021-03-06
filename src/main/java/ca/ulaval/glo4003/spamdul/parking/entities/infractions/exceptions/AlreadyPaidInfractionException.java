package ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions;

import ca.ulaval.glo4003.spamdul.assemblers.infraction.exceptions.InvalidInfractionException;

public class AlreadyPaidInfractionException extends InvalidInfractionException {

  public String getError() {
    return "ALREADY_PAID_INFRACTION";
  }

  public String getDescription() {
    return "Infraction already paid";
  }
}
