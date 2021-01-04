package ca.ulaval.glo4003.spamdul.parking2.entities.infraction;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class InfractionId extends Id {

  private InfractionId(String value) {
    super(value);
  }

  public static InfractionId valueOf(String value) {
    return new InfractionId(value);
  }
}
