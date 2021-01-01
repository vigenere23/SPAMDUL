package ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.util.List;

public class UsageReportDto {

  public ParkingZone parkingZone;
  public ParkingCategory parkingCategory;
  public List<UsageReportDayDto> usageReport;
  public Integer totalOfEntry;
}
