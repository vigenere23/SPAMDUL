package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DayOfWeekTest {

    @Test
    public void whenCheckingInclusionSameJavaDayOfWeek_shouldBeTrue() {
        final String STRING_DAY_OF_WEEK = "MONDAY";
        final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.valueOf(STRING_DAY_OF_WEEK);
        final DayOfWeek DAY_OF_WEEK = DayOfWeek.valueOf(STRING_DAY_OF_WEEK);

        boolean expected = DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

        assertThat(expected).isTrue();
    }

    @Test
    public void givenAllDayOfWeek_whenCheckingInclusionOfAnyJavaDayOfWeek_shouldBeTrue() {
        final String STRING_DAY_OF_WEEK = "MONDAY";
        final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.valueOf(STRING_DAY_OF_WEEK);
        final DayOfWeek ALL_DAY_OF_WEEK = DayOfWeek.ALL;

        boolean expected = ALL_DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

        assertThat(expected).isTrue();
    }

    @Test
    public void givenDayOfWeek_whenCheckingInclusionOfOtherJavaDayOfWeek_shouldBeFalse() {
        final java.time.DayOfWeek JAVA_DAY_OF_WEEK = java.time.DayOfWeek.MONDAY;
        final DayOfWeek ALL_DAY_OF_WEEK = DayOfWeek.TUESDAY;

        boolean expected = ALL_DAY_OF_WEEK.include(JAVA_DAY_OF_WEEK);

        assertThat(expected).isFalse();
    }

    @Test
    public void givenDayOfWeek_whenCheckingIfIncludedInSameDayOfWeek_shouldBeTrue() {
        final DayOfWeek DAY_OF_WEEK = DayOfWeek.MONDAY;
        final DayOfWeek OTHER_DAY_OF_WEEK = DayOfWeek.MONDAY;

        boolean result = DAY_OF_WEEK.includedIn(OTHER_DAY_OF_WEEK);

        assertThat(result).isTrue();
    }

    @Test
    public void givenDayOfWeek_whenCheckingIfIncludedInAllDayOfWeek_shouldBeTrue() {
        final DayOfWeek DAY_OF_WEEK = DayOfWeek.MONDAY;
        final DayOfWeek ALL_DAY_OF_WEEK = DayOfWeek.ALL;

        boolean result = DAY_OF_WEEK.includedIn(ALL_DAY_OF_WEEK);

        assertThat(result).isTrue();
    }

    @Test
    public void givenDayOfWeek_whenCheckingIfIncludedInOtherDayOfWeek_shouldBeFalse() {
        final DayOfWeek DAY_OF_WEEK = DayOfWeek.MONDAY;
        final DayOfWeek OTHER_DAY_OF_WEEK = DayOfWeek.FRIDAY;

        boolean result = DAY_OF_WEEK.includedIn(OTHER_DAY_OF_WEEK);

        assertThat(result).isFalse();
    }
}
