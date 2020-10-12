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

  public static PeriodType parse(String periodTypeString, Collator collator) {
    if (collator.equals("1h", periodTypeString.toLowerCase())) {
      return PeriodType.ONE_HOUR;
    } else if (collator.equals("1j", periodTypeString.toLowerCase())) {
      return PeriodType.SINGLE_DAY;
    } else if (collator.equals("1j/semaine/session", periodTypeString.toLowerCase())) {
      return PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
    } else if (collator.equals("1 session", periodTypeString.toLowerCase())) {
      return PeriodType.ONE_SEMESTER;
    } else if (collator.equals("2 session", periodTypeString.toLowerCase())) {
      return PeriodType.TWO_SEMESTERS;
    } else if (collator.equals("3 session", periodTypeString.toLowerCase())) {
      return PeriodType.THREE_SEMESTERS;
    } else if (collator.equals("mensuel", periodTypeString.toLowerCase())) {
      return PeriodType.MONTHLY;
    } else {
      return null;
    }
  }
}
