package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.DayOfWeek;

public class CampusAccessFactory {

  public CampusAccess create(UserId userId, CarId carId, Period period, DayOfWeek dayToAccessCampus) {
    if (dayToAccessCampus == DayOfWeek.SATURDAY || dayToAccessCampus == DayOfWeek.SUNDAY) {
      throw new InvalidDayToAccessCampusException("The campus access day must be between Monday and Friday");
    }
    return new CampusAccess(new CampusAccessCode(), userId, carId, dayToAccessCampus, period);
  }
}
