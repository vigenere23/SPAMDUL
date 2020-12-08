package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassPeriodTypeException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;

import java.util.ArrayList;

import static ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType.*;
import static jersey.repackaged.com.google.common.collect.Lists.newArrayList;

public class PassFactory {
  private final static ArrayList<PeriodType> ACCEPTED_PERIOD_TYPES = newArrayList(
          SINGLE_DAY_PER_WEEK_PER_SEMESTER,
          MONTHLY,
          ONE_SEMESTER,
          TWO_SEMESTERS,
          THREE_SEMESTERS);

  private final PassCodeFactory passCodeFactory;
  private final TimePeriodFactory timePeriodFactory;

  public PassFactory(PassCodeFactory passCodeFactory,
                     TimePeriodFactory timePeriodFactory) {
    this.passCodeFactory = passCodeFactory;
    this.timePeriodFactory = timePeriodFactory;
  }

  public Pass create(ParkingZone parkingZone, TimePeriodDto timePeriodDto) {
    if (!ACCEPTED_PERIOD_TYPES.contains(timePeriodDto.periodType)) {
      throw new InvalidPassPeriodTypeException();
    }
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);
    PassCode passCode = passCodeFactory.create();

    return new Pass(passCode, parkingZone, timePeriod);
  }
}
