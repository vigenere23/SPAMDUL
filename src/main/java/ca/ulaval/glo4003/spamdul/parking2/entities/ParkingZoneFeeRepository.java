package ca.ulaval.glo4003.spamdul.parking2.entities;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public interface ParkingZoneFeeRepository {

  Amount findBy(ParkingZone parkingZone, AccessPeriodType accessPeriodType);
}
