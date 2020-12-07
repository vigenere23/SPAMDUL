package ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions;

public class InvalidPassPeriodTypeException extends RuntimeException {
  private static final String MESSAGE = "The peridod type for a pass must be one of the followings "
          + "(single_day_per_week_per_semester, monthly, one_semester, two_semesters or three_semesters) ";

  public InvalidPassPeriodTypeException() {
    super(MESSAGE);
  }
}
