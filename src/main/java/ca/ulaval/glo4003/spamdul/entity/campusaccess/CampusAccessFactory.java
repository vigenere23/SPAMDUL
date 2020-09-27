package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
import java.time.DayOfWeek;

public class CampusAccessFactory {

  public CampusAccess create(UserId userId, CarId carId, Period period, DayOfWeek dayToAccessCampus) {
    if (dayToAccessCampus == DayOfWeek.SATURDAY || dayToAccessCampus == DayOfWeek.SUNDAY) {
      throw new InvalidDayToAccessCampusException("The campus access day must be between Monday and Friday");
    }

    if (period != Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER) {
      throw new InvalidPeriodArgumentException(
          "When choosing a specific day of the week to access the campus the period option must be single_per_week_per_semester");
    }

    return new CampusAccess(new CampusAccessCode(), userId, carId, dayToAccessCampus, period);
  }
}
