package ca.ulaval.glo4003.spamdul.entity.pass;

import java.text.Collator;

public enum ParkingZone {
  ALL,
  ZONE_1,
  ZONE_2,
  ZONE_3,
  ZONE_R,
  FREE;

  private static final Collator collator = Collator.getInstance();

  public static ParkingZone parse(String parkingZoneString) {
    collator.setStrength(Collator.NO_DECOMPOSITION);

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
