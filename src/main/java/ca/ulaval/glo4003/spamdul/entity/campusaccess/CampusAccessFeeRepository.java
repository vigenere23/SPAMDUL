package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;

public interface CampusAccessFeeRepository {

  Amount findBy(CarType carType, PeriodType period);
}
