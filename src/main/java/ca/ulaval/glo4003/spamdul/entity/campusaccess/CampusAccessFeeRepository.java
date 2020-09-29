package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarType;

public interface CampusAccessFeeRepository {

  public CampusAccessFee findFeeBy(CarType carType, Period period);
}
