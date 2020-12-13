package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public interface ParkingZoneFeeRepository {

  Amount findBy(ParkingZone parkingZone, PeriodType periodType);
}
