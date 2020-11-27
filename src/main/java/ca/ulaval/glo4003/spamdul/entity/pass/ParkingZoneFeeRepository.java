package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public interface ParkingZoneFeeRepository {

  Amount findBy(ParkingZone parkingZone, PeriodType periodType);
}
