package ca.ulaval.glo4003.spamdul.charging.entities.rechargul;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class RechargULCardId extends Id {

  private RechargULCardId(String value) {
    super(value);
  }

  public static RechargULCardId valueOf(String parkingAccessLogId) {
    return new RechargULCardId(parkingAccessLogId);
  }
}
