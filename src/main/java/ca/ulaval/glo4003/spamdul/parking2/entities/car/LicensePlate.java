package ca.ulaval.glo4003.spamdul.parking2.entities.car;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class LicensePlate extends Id {

  private LicensePlate(String value) {
    super(value);
  }

  public static LicensePlate valueOf(String value) {
    return new LicensePlate(value);
  }
}
