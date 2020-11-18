package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class CampusAccessFactory {

  private final TimePeriodFactory timePeriodFactory;

  public CampusAccessFactory(TimePeriodFactory timePeriodFactory) {
    this.timePeriodFactory = timePeriodFactory;
  }

  public CampusAccess create(UserId userId, CarId carId, TimePeriodDto timePeriodDto) {
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);

    return new CampusAccess(new CampusAccessCode(),
                            userId,
                            carId,
                            timePeriodDto.periodType,
                            timePeriod);
  }
}
