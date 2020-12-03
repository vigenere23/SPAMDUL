package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.exceptions;

public class InvalidTimePeriodArgumentException extends InvalidTimePeriodException{

  public InvalidTimePeriodArgumentException() {
    super("make a choice between (single_day_per_week_per_semester, monthly, one_semester, "
              + "two_semesters or three_semesters) ");
  }
}
