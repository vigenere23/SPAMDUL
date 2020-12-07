package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class InfractionId extends Id {

  private InfractionId(String value) {
    super(value);
  }

  public static InfractionId valueOf(String value) {
    return new InfractionId(value);
  }
}
