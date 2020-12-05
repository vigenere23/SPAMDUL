package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;

public class CampusAccessCode extends LongId {

  private CampusAccessCode(long value) {
    super(value);
  }

  public static CampusAccessCode valueOf(String userId) {
    try {
      return new CampusAccessCode(Long.parseLong(userId));
    } catch (NumberFormatException e) {
      throw new InvalidCampusAccessCodeFormatException("invalid campus code format");
    }
  }
}
