package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;

public class DailyCampusAccess extends CampusAccess {

  public DailyCampusAccess(CampusAccessCode campusAccessCode, TimePeriod timePeriod) {
    super(campusAccessCode, timePeriod);
  }

  @Override
  public ParkingZone getParkingZone() {
    return ParkingZone.ALL;
  }
}
