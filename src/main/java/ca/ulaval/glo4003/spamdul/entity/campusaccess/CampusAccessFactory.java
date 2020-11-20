package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class CampusAccessFactory {

  private final TimePeriodFactory timePeriodFactory;

  public CampusAccessFactory(TimePeriodFactory timePeriodFactory) {
    this.timePeriodFactory = timePeriodFactory;
  }

  public CampusAccess create(User user, Car car, TimePeriodDto timePeriodDto) {
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);

    if (timePeriodDto.periodType == PeriodType.HOURLY) {
      return new HourlyCampusAccess(new CampusAccessCode(),
              user,
              car,
              timePeriodDto.periodType,
              timePeriod,
              timePeriodDto.numberOfHours);
    } else {
      return new CampusAccess(new CampusAccessCode(),
                              user,
                              car,
                              timePeriodDto.periodType,
                              timePeriod);
    }
  }
}
