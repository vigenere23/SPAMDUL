package ca.ulaval.glo4003.spamdul.entity.charging_point;

import ca.ulaval.glo4003.spamdul.entity.ids.Id;

public class ChargingPointId extends Id {

  private ChargingPointId(String value) {
    super(value);
  }

  public static ChargingPointId valueOf(String parkingAccessLogId) {
    return new ChargingPointId(parkingAccessLogId);
  }
}
