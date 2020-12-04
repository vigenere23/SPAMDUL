package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidInfractionIdException;

public class InfractionId extends LongId {

  private InfractionId(long value) {
    super(value);
  }

  public static InfractionId valueOf(String id) {
    try {
      return new InfractionId(Long.parseLong(id));

    } catch (NumberFormatException e) {
      throw new InvalidInfractionIdException("Invalid infraction id format");
    }
  }
}
