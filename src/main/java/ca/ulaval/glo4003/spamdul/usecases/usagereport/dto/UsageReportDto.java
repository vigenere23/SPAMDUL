package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.util.List;

public class UsageReportDto {

  public ParkingZone parkingZone;
  public List<UsageReportDayDto> usageReport;
  public Integer totalOfEntry;
}
