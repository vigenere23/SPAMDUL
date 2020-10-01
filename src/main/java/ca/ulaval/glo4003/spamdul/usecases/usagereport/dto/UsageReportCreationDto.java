package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class UsageReportCreationDto {

  @NotNull
  public LocalDate startDate;
  @NotNull
  public LocalDate endDate;
  @NotNull
  public ParkingZone parkingZone;
}
