package ca.ulaval.glo4003.spamdul.shared.utils;

import java.time.format.DateTimeFormatter;

public class Formatters {

  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private Formatters() {
  }
}
