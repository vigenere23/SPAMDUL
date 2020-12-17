package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarType;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.PeriodType;

public interface CampusAccessFeeRepository {

  Amount findBy(CarType carType, PeriodType period);
}
