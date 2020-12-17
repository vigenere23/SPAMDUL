package ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class UsageReportCreationDto {

  @NotNull
  public LocalDate startDate;
  @NotNull
  public LocalDate endDate;
  public ParkingZone parkingZone;
  public ParkingCategory parkingCategory;
}
