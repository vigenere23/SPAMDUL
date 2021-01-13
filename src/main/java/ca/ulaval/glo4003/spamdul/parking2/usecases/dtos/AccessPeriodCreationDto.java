package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class AccessPeriodCreationDto {

  public AccessPeriodType type;
  public DayOfWeek dayOfWeek;
  public int year;
  public Month month;
  public Semester semester;
  public LocalDate date;
  public LocalTime startTime;
  public int numberOfHours;
}
