package ca.ulaval.glo4003.projet.base.ws.entity.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

public class User {

  private final String name;
  private final Gender gender;
  private final LocalDate birthdayDate;
  private final DayOfWeek dayToAccessCampus;
  private final UserId id;

  public User(String name, Gender gender, LocalDate birthdayDate, DayOfWeek dayToAccessCampus) {
    this.name = name;
    this.gender = gender;
    this.birthdayDate = birthdayDate;
    this.dayToAccessCampus = dayToAccessCampus;
    this.id = new UserId();
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthdayDate() {
    return birthdayDate;
  }

  public Gender getGender() {
    return gender;
  }

  public DayOfWeek getDayToAccessCampus() {
    return dayToAccessCampus;
  }

  public int getAge(LocalDate todaysDate) {
    //TODO si on passe une date inferieur a la naissance => probleme, age negatif, on veut le gerer?
    return Period.between(birthdayDate, todaysDate).getYears();
  }

  public UserId getId() {
    return id;
  }
}
