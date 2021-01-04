package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InfractionTypeNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.InvalidAccessException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;

public class InfractionTypeFactory {

  public InfractionType create(InvalidAccessException exception) {
    if (exception instanceof PermitNotFoundException) {
      return InfractionType.PERMIT_NOT_FOUND;
    }

    throw new InfractionTypeNotFoundException(exception);
  }
}
