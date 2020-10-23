package ca.ulaval.glo4003.spamdul.infrastructure.calendar;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Season;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Semester;
import java.time.LocalDate;
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

  @Test
  public void givenEndOfSummerSemester_whenGettingStartOfSchoolYear_shouldReturnStartOfPreviousYearAutumnSemester() {
    LocalDate endOfSummerSemester = hardCodedCalendar.getEndOfSemester(new Semester(Season.E, A_YEAR)).toLocalDate();
    LocalDateTime startOfPreviousAutumnSemester = hardCodedCalendar.getStartOfSemester(new Semester(Season.A,
                                                                                                    A_YEAR - 1));

    LocalDateTime startOfSchoolYear = hardCodedCalendar.getStartOfSchoolYearAtDate(endOfSummerSemester);

    assertThat(startOfSchoolYear).isEqualTo(startOfPreviousAutumnSemester);
  }

  @Test
  public void givenStartOfAutumnSemester_whenGettingStartOfSchoolYear_shouldReturnStartOfThatSemester() {
    LocalDateTime startOfAutumnSemester = hardCodedCalendar.getStartOfSemester(new Semester(Season.A, A_YEAR));
    LocalDateTime startOfSchoolYear = hardCodedCalendar.getStartOfSchoolYearAtDate(startOfAutumnSemester.toLocalDate());
    assertThat(startOfSchoolYear).isEqualTo(startOfAutumnSemester);
  }

  @Test
  public void givenEndOfSummerSemester_whenGettingEndOfSchoolYear_shouldReturnEndOfThatSemester() {
    LocalDateTime endOfSummerSemester = hardCodedCalendar.getEndOfSemester(new Semester(Season.E, A_YEAR));
    LocalDateTime endOfSchoolYear = hardCodedCalendar.getEndOfSchoolYearAtDate(endOfSummerSemester.toLocalDate());
    assertThat(endOfSchoolYear).isEqualTo(endOfSummerSemester);
  }

  @Test
  public void givenStartOfAutumnSemester_whenGettingEndOfSchoolYear_shouldReturnEndOfNextYearSummerSemester() {
    LocalDate startOfAutumnSemester = hardCodedCalendar.getStartOfSemester(new Semester(Season.A, A_YEAR))
                                                       .toLocalDate();
    LocalDateTime endOfNextSummerSemester = hardCodedCalendar.getEndOfSemester(new Semester(Season.E, A_YEAR + 1));

    LocalDateTime endOfSchoolYear = hardCodedCalendar.getEndOfSchoolYearAtDate(startOfAutumnSemester);

    assertThat(endOfSchoolYear).isEqualTo(endOfNextSummerSemester);
  }
}
