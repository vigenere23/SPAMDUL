package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;

public interface CampusAccessFeeRepository {

  CampusAccessFee findBy(CarType carType, PeriodType period);
}
