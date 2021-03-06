package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import java.text.Collator;

public enum PeriodType {
  HOURLY,
  SINGLE_DAY,
  SINGLE_DAY_PER_WEEK_PER_SEMESTER,
  MONTHLY,
  ONE_SEMESTER,
  TWO_SEMESTERS,
  THREE_SEMESTERS;

  private static final Collator collator = Collator.getInstance();

  public static PeriodType parse(String periodTypeString) {
    collator.setStrength(Collator.NO_DECOMPOSITION);

    if (collator.equals("1h", periodTypeString.toLowerCase())) {
      return PeriodType.HOURLY;

    } else if (collator.equals("1j", periodTypeString.toLowerCase())) {
      return PeriodType.SINGLE_DAY;

    } else if (collator.equals("1j/sem/session", periodTypeString.toLowerCase())
        || collator.equals("1j/semaine/session", periodTypeString.toLowerCase())) {
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
