package ca.ulaval.glo4003.spamdul.utils;

public class DateTimeFormatter {

  public static final java.time.format.DateTimeFormatter BIRTHDAY_DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final java.time.format.DateTimeFormatter DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  private DateTimeFormatter() {
  }
}
