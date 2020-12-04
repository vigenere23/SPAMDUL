package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.ids.LongId;

public class ParkingAccessLogId extends LongId {

  private ParkingAccessLogId(long value) {
    super(value);
  }

  public static ParkingAccessLogId valueOf(String parkingAccessLogId) {
    return new ParkingAccessLogId(Long.parseLong(parkingAccessLogId));
  }
}
