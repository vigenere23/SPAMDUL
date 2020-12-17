package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;

public interface ParkingZoneFeeRepository {

  Amount findBy(ParkingZone parkingZone, PeriodType periodType);
}
