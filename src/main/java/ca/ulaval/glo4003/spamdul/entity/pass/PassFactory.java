package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;

public class PassFactory {

  private final PassCodeFactory passCodeFactory;
  private final TimePeriodFactory timePeriodFactory;

  public PassFactory(PassCodeFactory passCodeFactory,
                     TimePeriodFactory timePeriodFactory) {
    this.passCodeFactory = passCodeFactory;
    this.timePeriodFactory = timePeriodFactory;
  }

  public Pass create(ParkingZone parkingZone, TimePeriodDto timePeriodDto) {
    TimePeriod timePeriod = timePeriodFactory.createTimePeriod(timePeriodDto);
    PassCode passCode = passCodeFactory.create();

    return new Pass(passCode, parkingZone, timePeriod);
  }
}
