package ca.ulaval.glo4003.spamdul.parking2.entities.permit;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class PermitNumber extends Id {

  private PermitNumber(String value) {
    super(value);
  }

  public static PermitNumber valueOf(String value) {
    return new PermitNumber(value);
  }
}
