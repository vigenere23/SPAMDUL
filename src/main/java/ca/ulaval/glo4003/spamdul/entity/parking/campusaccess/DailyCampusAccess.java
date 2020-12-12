package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;

public class DailyCampusAccess extends CampusAccess{
  public DailyCampusAccess(CampusAccessCode campusAccessCode, TimePeriod timePeriod) {
    super(campusAccessCode, timePeriod);
  }

  @Override
  public ParkingZone getParkingZone() {
    return ParkingZone.ALL;
  }
}
