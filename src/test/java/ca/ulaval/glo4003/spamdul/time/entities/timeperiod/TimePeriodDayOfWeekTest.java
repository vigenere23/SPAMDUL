package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class TimePeriodDayOfWeekTest {

  @Test
  public void whenCheckingInclusionSameJavaDayOfWeek_shouldBeTrue() {
    final String STRING_DAY_OF_WEEK = "MONDAY";
    final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.valueOf(STRING_DAY_OF_WEEK);
    final TimePeriodDayOfWeek DAY_OF_WEEK = TimePeriodDayOfWeek.valueOf(STRING_DAY_OF_WEEK);

    boolean expected = DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

    assertThat(expected).isTrue();
  }

  @Test
  public void givenAllDayOfWeek_whenCheckingInclusionOfAnyJavaDayOfWeek_shouldBeTrue() {
    final String STRING_DAY_OF_WEEK = "MONDAY";
    final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.valueOf(STRING_DAY_OF_WEEK);
    final TimePeriodDayOfWeek ALL_DAY_OF_WEEK = TimePeriodDayOfWeek.ALL;

    boolean expected = ALL_DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

    assertThat(expected).isTrue();
  }

  @Test
  public void givenDayOfWeek_whenCheckingInclusionOfOtherJavaDayOfWeek_shouldBeFalse() {
    final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.MONDAY;
    final TimePeriodDayOfWeek ALL_DAY_OF_WEEK = TimePeriodDayOfWeek.TUESDAY;

    boolean expected = ALL_DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

    assertThat(expected).isFalse();
  }

  @Test
  public void givenDayOfWeek_whenCheckingIfIncludedInSameDayOfWeek_shouldBeTrue() {
    final TimePeriodDayOfWeek DAY_OF_WEEK = TimePeriodDayOfWeek.MONDAY;
    final TimePeriodDayOfWeek OTHER_DAY_OF_WEEK = TimePeriodDayOfWeek.MONDAY;

    boolean result = DAY_OF_WEEK.includedIn(OTHER_DAY_OF_WEEK);

    assertThat(result).isTrue();
  }

  @Test
  public void givenDayOfWeek_whenCheckingIfIncludedInAllDayOfWeek_shouldBeTrue() {
    final TimePeriodDayOfWeek DAY_OF_WEEK = TimePeriodDayOfWeek.MONDAY;
    final TimePeriodDayOfWeek ALL_DAY_OF_WEEK = TimePeriodDayOfWeek.ALL;

    boolean result = DAY_OF_WEEK.includedIn(ALL_DAY_OF_WEEK);

    assertThat(result).isTrue();
  }

  @Test
  public void givenDayOfWeek_whenCheckingIfIncludedInOtherDayOfWeek_shouldBeFalse() {
    final TimePeriodDayOfWeek DAY_OF_WEEK = TimePeriodDayOfWeek.MONDAY;
    final TimePeriodDayOfWeek OTHER_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;

    boolean result = DAY_OF_WEEK.includedIn(OTHER_DAY_OF_WEEK);

    assertThat(result).isFalse();
  }
}
