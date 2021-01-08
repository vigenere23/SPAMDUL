package ca.ulaval.glo4003.spamdul.parking2.entities.access.period;

import java.text.Collator;

public enum AccessPeriodType {
  HOUR,
  DAY,
  DAY_PER_WEEK,
  MONTH,
  ONE_SEMESTER,
  TWO_SEMESTERS,
  THREE_SEMESTERS;

  private static final Collator collator = Collator.getInstance();

  public static AccessPeriodType parse(String periodTypeString) {
    collator.setStrength(Collator.NO_DECOMPOSITION);

    if (collator.equals("1h", periodTypeString.toLowerCase())) {
      return AccessPeriodType.HOUR;

    } else if (collator.equals("1j", periodTypeString.toLowerCase())) {
      return AccessPeriodType.DAY;

    } else if (collator.equals("1j/sem/session", periodTypeString.toLowerCase())
        || collator.equals("1j/semaine/session", periodTypeString.toLowerCase())) {
      return AccessPeriodType.DAY_PER_WEEK;

    } else if (collator.equals("1 session", periodTypeString.toLowerCase())) {
      return AccessPeriodType.ONE_SEMESTER;

    } else if (collator.equals("2 session", periodTypeString.toLowerCase())) {
      return AccessPeriodType.TWO_SEMESTERS;

    } else if (collator.equals("3 session", periodTypeString.toLowerCase())) {
      return AccessPeriodType.THREE_SEMESTERS;

    } else if (collator.equals("mensuel", periodTypeString.toLowerCase())) {
      return AccessPeriodType.MONTH;

    } else {
      return null;
    }
  }
}
