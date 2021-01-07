package ca.ulaval.glo4003.spamdul.parking2.entities;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;

public interface ParkingCarFeeRepository {

  Amount findBy(CarType carType, AccessPeriodType accessPeriodType);
}
