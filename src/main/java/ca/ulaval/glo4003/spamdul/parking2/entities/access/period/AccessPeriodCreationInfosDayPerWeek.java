package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;

public class AccessPeriodCreationInfosDayPerWeek {

  private final Semester semester;
  private final DayOfWeek dayOfWeek;

  public AccessPeriodCreationInfosDayPerWeek(Semester semester, DayOfWeek dayOfWeek) {
    this.semester = semester;
    this.dayOfWeek = dayOfWeek;
  }

  public Semester getSemester() {
    return this.semester;
  }

  public DayOfWeek getDayOfWeek() {
    return this.dayOfWeek;
  }
}
