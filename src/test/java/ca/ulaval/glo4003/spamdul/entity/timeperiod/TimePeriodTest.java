package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import static com.google.common.truth.Truth.assertThat;

import java.time.LocalDateTime;
import org.junit.Test;

public class TimePeriodTest {

  private final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private final LocalDateTime A_WEDNESDAY_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 15, 0, 0);
  private final LocalDateTime A_DATE_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 13, 0, 0);
  private final LocalDateTime A_DATE_TIME_BEFORE = LocalDateTime.of(2019, 1, 1, 0, 0);
  private final LocalDateTime A_DATE_TIME_AFTER = LocalDateTime.of(2021, 1, 1, 0, 0);
  private final TimePeriodDayOfWeek A_DAY_OF_WEEK = TimePeriodDayOfWeek.MONDAY;

  @Test
  public void givenSameTimePeriods_whenComparingTimePeriod_shouldBeEqual() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod SAME_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);

    boolean result = A_TIME_PERIOD.equals(SAME_TIME_PERIOD);

    assertThat(result).isTrue();
  }

  @Test
  public void givenDifferentTimePeriods_whenComparing_shouldNotBeEqual() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod OTHER_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = A_TIME_PERIOD.equals(OTHER_TIME_PERIOD);

    assertThat(result).isFalse();
  }

  @Test
  public void givenDayOfWeekIncluded_whenCheckingInclusionOfJavaDateTimeInTheMiddle_shouldBeTrue() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);

    boolean result = TIME_PERIOD.includes(A_WEDNESDAY_IN_THE_MIDDLE);

    assertThat(result).isTrue();
  }

  @Test
  public void givenDayOfWeekNotIncluded_whenCheckingInclusionOfJavaDateTimeInTheMiddle_shouldBeFalse() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = TIME_PERIOD.includes(A_WEDNESDAY_IN_THE_MIDDLE);

    assertThat(result).isFalse();
  }

  @Test
  public void whenCheckingInclusionOfJavaDateTimeBefore_shouldBeFalse() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = TIME_PERIOD.includes(A_DATE_TIME_BEFORE);

    assertThat(result).isFalse();
  }

  @Test
  public void whenCheckingInclusionOfJavaDateTimeAfter_shouldBeFalse() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = TIME_PERIOD.includes(A_DATE_TIME_AFTER);

    assertThat(result).isFalse();
  }

  @Test
  public void givenTimePeriod_whenCheckingIfIncludedInSameTimePeriod_shouldBeTrue() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod SAME_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);

    boolean result = A_TIME_PERIOD.includedIn(SAME_TIME_PERIOD);

    assertThat(result).isTrue();
  }

  @Test
  public void whenCheckingBoundsOfJavaDateTimeBefore_shouldBeFalse() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = TIME_PERIOD.bounds(A_DATE_TIME_BEFORE);

    assertThat(result).isFalse();
  }

  @Test
  public void whenCheckingBoundsOfJavaDateTimeAfter_shouldBeFalse() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);

    boolean result = TIME_PERIOD.bounds(A_DATE_TIME_AFTER);

    assertThat(result).isFalse();
  }

  @Test
  public void whenCheckingBoundsOfJavaDateTimeInTheMiddle_shouldBeTrue() {
    final TimePeriod TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);

    boolean result = TIME_PERIOD.bounds(A_DATE_IN_THE_MIDDLE);

    assertThat(result).isTrue();
  }


  @Test
  public void givenTimePeriod_whenCheckingIfIncludedInTimePeriodStartingBeforeAndEndingAfterWithDayIncluded_shouldBeTrue() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod INCLUDING_TIME_PERIOD = new TimePeriod(A_DATE_TIME_BEFORE, A_DATE_TIME_AFTER, A_DAY_OF_WEEK);

    boolean result = A_TIME_PERIOD.includedIn(INCLUDING_TIME_PERIOD);

    assertThat(result).isTrue();
  }

  @Test
  public void givenTimePeriod_whenCheckingIfIncludedInTimePeriodEndingBefore_shouldBeFalse() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod TIME_PERIOD_ENDING_BEFORE = new TimePeriod(A_DATE_TIME_BEFORE, A_START_DATE_TIME, A_DAY_OF_WEEK);

    boolean result = A_TIME_PERIOD.includedIn(TIME_PERIOD_ENDING_BEFORE);

    assertThat(result).isFalse();
  }

  @Test
  public void givenTimePeriod_whenCheckingIfIncludedInTimePeriodStartingAfter_shouldBeFalse() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_DAY_OF_WEEK);
    TimePeriod TIME_PERIOD_STARTING_AFTER = new TimePeriod(A_END_DATE_TIME, A_DATE_TIME_AFTER, A_DAY_OF_WEEK);

    boolean result = A_TIME_PERIOD.includedIn(TIME_PERIOD_STARTING_AFTER);

    assertThat(result).isFalse();
  }

  @Test
  public void givenTimePeriod_whenCheckingIfIncludedInTimePeriodWithDayOfWeekNotIncluded_shouldBeFalse() {
    TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.FRIDAY);
    TimePeriod TIME_PERIOD_NOT_SAME_DAY = new TimePeriod(A_DATE_TIME_BEFORE,
                                                         A_DATE_TIME_AFTER,
                                                         TimePeriodDayOfWeek.MONDAY);

    boolean result = A_TIME_PERIOD.includedIn(TIME_PERIOD_NOT_SAME_DAY);

    assertThat(result).isFalse();
  }
}
