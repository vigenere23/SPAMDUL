package ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;

public class BikeParkingAccess extends Pass {

  public BikeParkingAccess(BikeParkingAccessCode bikeParkingAccessCode, TimePeriod timePeriod) {
    super(bikeParkingAccessCode, ParkingZone.ZONE_BIKE, timePeriod);
  }
}
