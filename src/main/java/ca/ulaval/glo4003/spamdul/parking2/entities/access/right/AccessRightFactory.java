package ca.ulaval.glo4003.spamdul.parking2.entities.access.right;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodFactory;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;

public class AccessRightFactory {

  private final AccessPeriodFactory accessPeriodFactory;

  public AccessRightFactory(AccessPeriodFactory accessPeriodFactory) {
    this.accessPeriodFactory = accessPeriodFactory;
  }

  public AccessRight create(AccessPeriodType periodType, ParkingZone parkingZone, AccessPeriodCreationInfos infos) {
    AccessPeriod accessPeriod = accessPeriodFactory.create(periodType, infos);
    return new AccessRight(parkingZone, accessPeriod);
  }
}
