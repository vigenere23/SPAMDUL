package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.charging_point.exceptions.InvalidChargingPointIdException;
import ca.ulaval.glo4003.spamdul.entity.ids.LongId;

public class ChargingPointId extends LongId {

  private ChargingPointId(long value) {
    super(value);
  }

  public static ChargingPointId valueOf(String parkingAccessLogId) {
    try {
      return new ChargingPointId(Long.parseLong(parkingAccessLogId));
    } catch (Exception e) {
      throw new InvalidChargingPointIdException();
    }
  }
}
