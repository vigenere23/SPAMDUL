package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;

public class UsageReportSummaryDto {

  public float meanUsagePerDay;
  public LocalDate mostPopularMonthDate;
  public LocalDate leastPopularMonthDate;
  public ParkingZone parkingZone;

  @Override
  public String toString() {
    return this.getClass().getName() + "\n{\n  meanUsagePerDay = " + meanUsagePerDay + "\n  mostPopularMonthDate = "
        + mostPopularMonthDate + "\n  leastPopularMonthDate = " + leastPopularMonthDate + "\n parkingZone = "
        + parkingZone + "\n}";
  }
}
