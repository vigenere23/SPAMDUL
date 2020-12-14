package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.util.List;

public class UsageReportDto {

  public ParkingZone parkingZone;
  public ParkingCategory parkingCategory;
  public List<UsageReportDayDto> usageReport;
  public Integer totalOfEntry;
}
