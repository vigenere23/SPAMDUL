package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.ParkingZone;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccessRightDto {

  public ParkingZone parkingZone;
  public LocalDate periodStart;
  public LocalDate periodEnd;
  public LocalTime startTime;
  public LocalTime endTime;
  public DayOfWeek dayOfWeek;
}
