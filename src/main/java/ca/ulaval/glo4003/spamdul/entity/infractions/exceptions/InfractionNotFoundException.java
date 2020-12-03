package ca.ulaval.glo4003.spamdul.entity.infractions.exceptions;

import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;

public class InfractionNotFoundException extends RuntimeException {

  public InfractionNotFoundException(InfractionId infractionId) {
    super(String.format("No infraction with id: %s", infractionId));
  }
}
