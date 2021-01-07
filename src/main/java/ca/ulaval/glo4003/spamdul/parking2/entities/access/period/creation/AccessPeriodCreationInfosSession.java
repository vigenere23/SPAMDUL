package ca.ulaval.glo4003.spamdul.parking2.entities.access.period.creation;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;

public class AccessPeriodCreationInfosSession {

  private final Semester semester;

  public AccessPeriodCreationInfosSession(Semester semester) {
    this.semester = semester;
  }

  public Semester getSemester() {
    return this.semester;
  }
}
