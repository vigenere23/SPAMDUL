package ca.ulaval.glo4003.spamdul.infrastructure.db.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import java.text.Collator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodTypeParser {

  public static Map<PeriodType, Integer> mapPeriodColumn(List<String> csvInfos,
                                                   Collator collator) {
    Map<PeriodType, Integer> periodTypePosition = new HashMap<>();

    for (int i = 1; i < csvInfos.size(); i++) {
      String info = csvInfos.get(i);
      if (collator.equals("1h", info)) {
        periodTypePosition.put(PeriodType.ONE_HOUR, i);
      } else if (collator.equals("1j", info)) {
        periodTypePosition.put(PeriodType.SINGLE_DAY, i);
      } else if (collator.equals("1j/semaine/session", info)) {
        periodTypePosition.put(PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, i);
      } else if (collator.equals("1 session", info)) {
        periodTypePosition.put(PeriodType.ONE_SEMESTER, i);
      } else if (collator.equals("2 session", info)) {
        periodTypePosition.put(PeriodType.TWO_SEMESTERS, i);
      } else if (collator.equals("3 session", info)) {
        periodTypePosition.put(PeriodType.THREE_SEMESTERS, i);
      }
    }
    return periodTypePosition;
  }
}
