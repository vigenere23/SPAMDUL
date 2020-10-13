package ca.ulaval.glo4003.spamdul.entity.timeperiod;

import java.text.Collator;

public enum PeriodType {
  ONE_HOUR,
  SINGLE_DAY,
  SINGLE_DAY_PER_WEEK_PER_SEMESTER,
  MONTHLY,
  ONE_SEMESTER,
  TWO_SEMESTERS,
  THREE_SEMESTERS;

  private static final Collator collator = Collator.getInstance();

  public static PeriodType parse(String periodTypeString) {
    collator.setStrength(Collator.NO_DECOMPOSITION);

    if (collator.equals("1h", periodTypeString)) {
      return PeriodType.ONE_HOUR;
    } else if (collator.equals("1j", periodTypeString)) {
      return PeriodType.SINGLE_DAY;
    } else if (collator.equals("1j/semaine/session", periodTypeString)) {
      return PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
    } else if (collator.equals("1 session", periodTypeString)) {
      return PeriodType.ONE_SEMESTER;
    } else if (collator.equals("2 session", periodTypeString)) {
      return PeriodType.TWO_SEMESTERS;
    } else if (collator.equals("3 session", periodTypeString)) {
      return PeriodType.THREE_SEMESTERS;
    } else {
      return null;
    }
  }
}
