package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;

public class CampusAccessFactory {

  private final TimePeriodFactory timePeriodFactory;

  public CampusAccessFactory(TimePeriodFactory timePeriodFactory) {
    this.timePeriodFactory = timePeriodFactory;
  }

  public CampusAccess create(TimePeriodDto timePeriodDto) {
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);

    if (timePeriodDto.periodType == PeriodType.HOURLY) {
      return new HourlyCampusAccess(new CampusAccessCode(),
              timePeriodDto.periodType,
              timePeriod,
              timePeriodDto.numberOfHours);
    } else {
      return new CampusAccess(new CampusAccessCode(),
                              timePeriodDto.periodType,
                              timePeriod);
    }
  }
}
