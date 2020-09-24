package ca.ulaval.glo4003.spamdul.entity.user;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class UserFactory {

  public User create(String name, Gender gender, LocalDate birthDate, DayOfWeek dayToAccessCampus) {
    //TODO Utile si le client valide que c<est bien le cas, ne pas oublier de decommenter les tests
//    if (dayToAccessCampus == DayOfWeek.SATURDAY || dayToAccessCampus == DayOfWeek.SUNDAY) {
//      throw new InvalidDayToAccessCampusException("The day to access the campus must be between Monday and Friday");
//    }

    return new User(new UserId(), name, gender, birthDate, dayToAccessCampus);
  }
}
