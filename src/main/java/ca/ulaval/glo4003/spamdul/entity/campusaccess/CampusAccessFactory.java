package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class CampusAccessFactory {
  private final TimePeriodFactory timePeriodFactory;

  public CampusAccessFactory(TimePeriodFactory timePeriodFactory) {
    this.timePeriodFactory = timePeriodFactory;
  }

  public CampusAccess create(UserId userId, CarId carId, TimePeriodDto timePeriodDto) {
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);

    // TODO: this will go in the assembler since our dayOfWeeks dont have weekends
/*    if (timePeriod.getDayOfWeek() == DayOfWeek.SATURDAY || timePeriod.getDayOfWeek() == DayOfWeek.SUNDAY) {
      throw new InvalidDayToAccessCampusException("The campus access day must be between Monday and Friday");
    }

    // TODO: this too!!!
    if (timePeriodDto.periodType != PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      throw new InvalidPeriodArgumentException(
          "When choosing a specific day of the week to access the campus the period option must be single_per_week_per_semester");
    }*/

    return new CampusAccess(new CampusAccessCode(), userId, carId, timePeriod);
  }
}
