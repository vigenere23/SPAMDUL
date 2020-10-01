package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassDayOfWeekException;

import java.time.DayOfWeek;

public class PassFactory {

  public PassFactory() {
  }

  public Pass create(ParkingZone parkingZone, PassType passType, DayOfWeek dayOfWeek) {
    PassCode passCode = new PassCode();

    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
      throw new InvalidPassDayOfWeekException("The campus access day must be between Monday and Friday");
    }

    if (passType != PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      throw new InvalidPassArgumentException(
              "When choosing a specific day of the week for the pass the pass type must be single_per_week_per_semester");
    }

    return new Pass(passCode, parkingZone, passType, dayOfWeek);
  }
}
