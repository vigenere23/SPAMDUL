package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.Id;

public class ChargingPointId extends Id {

  private ChargingPointId(String value) {
    super(value);
  }

  public static ChargingPointId valueOf(String parkingAccessLogId) {
    return new ChargingPointId(parkingAccessLogId);
  }
}
