package ca.ulaval.glo4003.spamdul.entity.pass;

import java.text.Collator;

public enum ParkingZone {
  ZONE_1,
  ZONE_2,
  ZONE_3,
  ZONE_R;

  public static ParkingZone parse(String parkingZoneString, Collator collator) {
    if (collator.equals("zone1", parkingZoneString.toLowerCase())) {
      return ParkingZone.ZONE_1;
    } else if (collator.equals("zone2", parkingZoneString.toLowerCase())) {
      return ParkingZone.ZONE_2;
    } else if (collator.equals("zone3", parkingZoneString.toLowerCase())) {
      return ParkingZone.ZONE_3;
    } else if (collator.equals("zoner", parkingZoneString.toLowerCase())) {
      return ParkingZone.ZONE_R;
    } else {
      return null;
    }
  }
}
