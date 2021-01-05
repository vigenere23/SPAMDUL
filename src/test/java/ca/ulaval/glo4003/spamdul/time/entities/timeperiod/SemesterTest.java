package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class SemesterTest {

  @Test
  public void givenSameSemester_whenComparingSemester_shouldBeEqual() {
    Semester A_SEMESTER = new Semester(Session.AUTUMN, 2020);
    Semester SAME_SEMESTER = new Semester(Session.AUTUMN, 2020);

    boolean result = A_SEMESTER.equals(SAME_SEMESTER);

    assertThat(result).isTrue();
  }

  @Test
  public void givenDifferentSemester_whenComparingSemester_shouldNotBeEqual() {
    final Semester A_SEMESTER = new Semester(Session.AUTUMN, 2020);
    final Semester OTHER_SEMESTER = new Semester(Session.WINTER, 2020);

    boolean result = A_SEMESTER.equals(OTHER_SEMESTER);

    assertThat(result).isFalse();
  }

  @Test
  public void givenAutumnSemester_whenAdding1Semester_ShouldBeWinterOfNextYear() {
    final Semester AUTUMN_SEMESTER = new Semester(Session.AUTUMN, 2020);

    Semester nextSemester = AUTUMN_SEMESTER.plusSemesters(1);

    assertThat(nextSemester).isEqualTo(new Semester(Session.WINTER, 2021));
  }

  @Test
  public void givenSummerSemester_whenAdding1Semester_ShouldBeAutumnOfSameYear() {
    final Semester WINTER_SEMESTER = new Semester(Session.SUMMER, 2020);

    Semester nextSemester = WINTER_SEMESTER.plusSemesters(1);

    assertThat(nextSemester).isEqualTo(new Semester(Session.AUTUMN, 2020));
  }

  @Test
  public void givenSummerSemester_whenAdding2Semester_ShouldBeWinterOfNextYear() {
    final Semester WINTER_SEMESTER = new Semester(Session.SUMMER, 2020);

    Semester nextSemester = WINTER_SEMESTER.plusSemesters(2);

    assertThat(nextSemester).isEqualTo(new Semester(Session.WINTER, 2021));
  }

  @Test
  public void givenWinterSemester_whenAdding3Semester_ShouldBeWinterOfNextYear() {
    final Semester WINTER_SEMESTER = new Semester(Session.WINTER, 2020);

    Semester nextSemester = WINTER_SEMESTER.plusSemesters(3);

    assertThat(nextSemester).isEqualTo(new Semester(Session.WINTER, 2021));
  }
}
