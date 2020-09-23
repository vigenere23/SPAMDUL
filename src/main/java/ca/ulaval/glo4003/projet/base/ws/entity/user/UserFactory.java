package ca.ulaval.glo4003.projet.base.ws.entity.user;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class UserFactory {

  public User create(String name, Gender gender, LocalDate birthDate, DayOfWeek dayToAccessCampus) {
    if (dayToAccessCampus == DayOfWeek.SATURDAY || dayToAccessCampus == DayOfWeek.SUNDAY) {
      throw new InvalidDayToAccessCampusException("The day to access the campus must be between Monday and Friday");
    }

    return new User(name, gender, birthDate, dayToAccessCampus);
  }
}
