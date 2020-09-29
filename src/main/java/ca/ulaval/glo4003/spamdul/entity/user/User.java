package ca.ulaval.glo4003.spamdul.entity.user;

import java.time.LocalDate;
import java.time.Period;

public class User {

  private final String name;
  private final Gender gender;
  private final LocalDate birthDate;
  private final UserId userId;

  public User(UserId userId, String name, Gender gender, LocalDate birthDate) {
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
    //TODO Devrait-on bouger ca dans la passe au moment ou elle est acheter?
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }

  public int getAge(LocalDate todaysDate) {
    //TODO si on passe une date inferieur a la naissance => probleme, age negatif, on veut le gerer?
    return Period.between(birthDate, todaysDate).getYears();
  }

  public UserId getUserId() {
    return userId;
  }
}
