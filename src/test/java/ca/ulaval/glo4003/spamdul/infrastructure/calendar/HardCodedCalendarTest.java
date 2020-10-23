package ca.ulaval.glo4003.spamdul.infrastructure.calendar;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Season;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import java.time.LocalDateTime;
import org.junit.Test;

public class HardCodedCalendarTest {

  private final int A_YEAR = 2098;

  private final HardCodedCalendar hardCodedCalendar = new HardCodedCalendar();

  @Test
  public void givenAutumnSemester_whenGettingStartDate_shouldReturn1SeptOfSameYear() {
    Semester semester = new Semester(Season.A, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getStartOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 9, 1, 0, 0));
  }

  @Test
  public void givenWinterSemester_whenGettingStartDate_shouldReturn1JanOfSameYear() {
    Semester semester = new Semester(Season.H, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getStartOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 1, 1, 0, 0));
  }

  @Test
  public void givenSummerSemester_whenGettingStartDate_shouldReturn1MayOfSameYear() {
    Semester semester = new Semester(Season.E, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getStartOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 5, 1, 0, 0));
  }

  @Test
  public void givenAutumnSemester_whenGettingEndDate_shouldReturn31DecOfSameYear() {
    Semester semester = new Semester(Season.A, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getEndOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 12, 31, 23, 59, 59));
  }

  @Test
  public void givenWinterSemester_whenGettingEndDate_shouldReturn30AprilOfSameYear() {
    Semester semester = new Semester(Season.H, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getEndOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 4, 30, 23, 59, 59));
  }

  @Test
  public void givenSummerSemester_whenGettingEndDate_shouldReturn31AugOfSameYear() {
    Semester semester = new Semester(Season.E, A_YEAR);

    LocalDateTime result = hardCodedCalendar.getEndOfSemester(semester);

    assertThat(result).isEqualTo(LocalDateTime.of(A_YEAR, 8, 31, 23, 59, 59));
  }
}
