package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;

public class CampusAccessFactory {

  private final CampusAccessCodeFactory campusAccessCodeFactory;
  private final TimePeriodFactory timePeriodFactory;

  public CampusAccessFactory(CampusAccessCodeFactory campusAccessCodeFactory,
                             TimePeriodFactory timePeriodFactory) {
    this.campusAccessCodeFactory = campusAccessCodeFactory;
    this.timePeriodFactory = timePeriodFactory;
  }

  public CampusAccess create(TimePeriodDto timePeriodDto) {
    if (timePeriodDto.periodType == PeriodType.HOURLY) {
      return new HourlyCampusAccess(campusAccessCodeFactory.create(),
                                    timePeriodDto.numberOfHours);
    }
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);
    if (timePeriodDto.periodType == PeriodType.SINGLE_DAY) {
      return new DailyCampusAccess(campusAccessCodeFactory.create(),
              timePeriod);
    } else {
      return new CampusAccess(campusAccessCodeFactory.create(),
                              timePeriod);
    }
  }
}
