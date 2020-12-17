package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class CampusAccessCode extends Id {

  private CampusAccessCode(String value) {
    super(value);
  }

  public static CampusAccessCode valueOf(String value) {
    return new CampusAccessCode(value);
  }
}
