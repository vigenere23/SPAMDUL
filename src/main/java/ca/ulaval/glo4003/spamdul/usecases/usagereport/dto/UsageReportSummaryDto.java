package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;

public class UsageReportSummaryDto {

  public float meanUsagePerDay;
  public LocalDate mostPopularMonthDate;
  public LocalDate leastPopularMonthDate;
  public ParkingZone parkingZone;
  public ParkingCategory parkingCategory;

  @Override
  public String toString() {
    return this.getClass().getName() + "\n{\n  meanUsagePerDay = " + meanUsagePerDay + "\n  mostPopularMonthDate = "
        + mostPopularMonthDate + "\n  leastPopularMonthDate = " + leastPopularMonthDate + "\n parkingZone = "
        + parkingZone + "\n}";
  }
}
