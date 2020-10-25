package ca.ulaval.glo4003.spamdul.utils;

public class DateTimeFormatter {

  public static final java.time.format.DateTimeFormatter BIRTHDAY_DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final java.time.format.DateTimeFormatter ACCESSING_CAMPUS_DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final java.time.format.DateTimeFormatter USAGE_REPORT_DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final java.time.format.DateTimeFormatter TRANSACTION_DATE_TIME_FORMATTER = java.time.format.DateTimeFormatter
      .ofPattern("yyyy-MM-dd");

  public static final java.time.format.DateTimeFormatter LOCAL_TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern(
      "HH:mm");

  private DateTimeFormatter() {
  }
}
