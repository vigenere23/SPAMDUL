package ca.ulaval.glo4003.projet.base.ws.domain.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

public class User {

  private String name;
  private LocalDate birthday;
  private Gender gender;
  private DayOfWeek dayToAccessCampus;
  private final UserId id;

  public User() {
    this.id = new UserId();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public DayOfWeek getDayToAccessCampus() {
    return dayToAccessCampus;
  }

  public void setDayToAccessCampus(DayOfWeek dayToAccessCampus) {
    this.dayToAccessCampus = dayToAccessCampus;
  }

  public int getAge(LocalDate todaysDate) {
    //TODO si on passe une date inferieur a la naissance => probleme, age negatif, on veut le gerer?
    return Period.between(birthday, todaysDate).getYears();
  }

  public UserId getId() {
    return id;
  }
}
