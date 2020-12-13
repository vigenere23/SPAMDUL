package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.user.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;

public interface CampusAccessFeeRepository {

  Amount findBy(CarType carType, PeriodType period);
}
