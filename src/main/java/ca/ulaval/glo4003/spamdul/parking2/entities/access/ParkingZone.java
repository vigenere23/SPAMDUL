package ca.ulaval.glo4003.spamdul.parking2.entities.access;

import java.text.Collator;

public enum ParkingZone {
  BIKE,
  ZONE_3,
  ZONE_2,
  ZONE_R,
  ZONE_1,
  ANY;

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
