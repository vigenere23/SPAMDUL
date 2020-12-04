package ca.ulaval.glo4003.spamdul.entity.rechargul;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;
import ca.ulaval.glo4003.spamdul.entity.rechargul.exceptions.InvalidRechargULCardIdException;

public class RechargULCardId extends LongId {

  private RechargULCardId(long value) {
    super(value);
  }

  public static RechargULCardId valueOf(String parkingAccessLogId) {
    try {
      return new RechargULCardId(Long.parseLong(parkingAccessLogId));
    } catch (Exception e) {
      throw new InvalidRechargULCardIdException();
    }
  }
}
