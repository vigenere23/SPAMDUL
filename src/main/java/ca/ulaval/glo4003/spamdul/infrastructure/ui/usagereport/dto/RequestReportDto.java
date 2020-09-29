package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import java.time.LocalDate;

public class RequestReportDto {
  public LocalDate startDate;
  public LocalDate endDate;
  public ParkingZone parkingZone;
}
