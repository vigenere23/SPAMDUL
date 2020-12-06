package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class ParkingAccessLogId extends Id {

  private ParkingAccessLogId(String value) {
    super(value);
  }

  public static ParkingAccessLogId valueOf(String parkingAccessLogId) {
    return new ParkingAccessLogId(parkingAccessLogId);
  }
}
