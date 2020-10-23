package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.util.Objects;

public class Semester {

  private final Season season;
  private final int year;

  public Semester(Season season, int year) {
    this.season = season;
    this.year = year;
  }

  public Semester addSemester(int numberOfSemester) {
    int remainder = numberOfSemester % 3;
    int plusYear = numberOfSemester / 3;
    if (remainder == 0) {
      return new Semester(season, year + plusYear);
    }
    return nextSemester().addSemester(numberOfSemester - 1);
  }

  private Semester nextSemester() {
    switch (season) {
      case A:
        return new Semester(Season.H, year + 1);
      case H:
        return new Semester(Season.E, year);
      case E:
        return new Semester(Season.A, year);
      default:
        throw new RuntimeException("The given season is not valid");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Semester semester = (Semester) o;
    return season == semester.season &&
        year == semester.year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(season, year);
  }

  public Season getSeason() {
    return season;
  }

  public int getYear() {
    return year;
  }
}
