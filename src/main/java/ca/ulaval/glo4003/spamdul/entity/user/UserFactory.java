package ca.ulaval.glo4003.spamdul.entity.user;

import java.time.LocalDate;

public class UserFactory {

  public User create(String name, Gender gender, LocalDate birthDate) {
    return new User(new UserId(), name, gender, birthDate);
  }
}
