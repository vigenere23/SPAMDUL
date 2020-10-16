package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;

public interface ParkingZoneFeeRepository {

  ParkingZoneFee findBy(ParkingZone parkingZone, PeriodType periodType);

}
