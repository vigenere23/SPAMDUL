package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.exception.InvalidSeasonException;
import java.util.Objects;

public class Semester {

  private final Session session;
  private final int year;

  public Semester(Session session, int year) {
    this.session = session;
    this.year = year;
  }

  public Semester addSemester(int numberOfSemester) {
    int remainder = numberOfSemester % 3;
    int plusYear = numberOfSemester / 3;

    if (remainder == 0) {
      return new Semester(session, year + plusYear);
    }

    return nextSemester().addSemester(numberOfSemester - 1);
  }

  private Semester nextSemester() {
    switch (session) {
      case AUTUMN:
        return new Semester(Session.WINTER, year + 1);

      case WINTER:
        return new Semester(Session.SUMMER, year);

      case SUMMER:
        return new Semester(Session.AUTUMN, year);

      default:
        throw new InvalidSeasonException();
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

    return session == semester.session &&
        year == semester.year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(session, year);
  }

  public Session getSeason() {
    return session;
  }

  public int getYear() {
    return year;
  }
}
