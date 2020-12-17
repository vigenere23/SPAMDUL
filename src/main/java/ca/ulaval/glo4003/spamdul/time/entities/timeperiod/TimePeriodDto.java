package ca.ulaval.glo4003.spamdul.time.entities.timeperiod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TimePeriodDto {

  public PeriodType periodType;
  public LocalDate startDate;
  public LocalDate date;
  public Semester semester;
  public TimePeriodDayOfWeek timePeriodDayOfWeek;
  public BigDecimal numberOfHours;
}
