package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriod;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodDay;
import ca.ulaval.glo4003.spamdul.shared.entities.timeperiod.TimePeriod;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessPeriodFactorySingleDay {

  public AccessPeriod create(AccessPeriodCreationInfosSingleDay infos) {
    LocalDate date = infos.getDate();
    TimePeriod timePeriod = new TimePeriod(date, date, LocalTime.MIN, LocalTime.MAX);

    return new AccessPeriodDay(timePeriod);
  }
}
