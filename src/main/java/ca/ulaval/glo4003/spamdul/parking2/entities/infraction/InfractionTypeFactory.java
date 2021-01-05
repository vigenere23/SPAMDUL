package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InfractionTypeNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidParkingZoneException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidPermitException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;

public class InfractionTypeFactory {

  public InfractionType create(InvalidAccessException exception) {
    if (exception instanceof PermitNotFoundException) {
      return InfractionType.NO_PERMIT;
    } else if (exception instanceof InvalidPermitException) {
      return InfractionType.INVALID_PERMIT;
    } else if (exception instanceof InvalidParkingZoneException) {
      return InfractionType.INVALID_ZONE;
    }

    throw new InfractionTypeNotFoundException(exception);
  }
}
