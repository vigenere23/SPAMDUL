package ca.ulaval.glo4003.spamdul.usage.entities.usagereport;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;
import java.util.Optional;

public class UsageReportSummary {

  private final float meanUsagePerDay;
  private final Optional<LocalDate> mostPopularDayOfMonth;
  private final Optional<LocalDate> leastPopularDayOfMonth;
  private final Optional<ParkingZone> parkingZone;
  private final Optional<ParkingCategory> parkingCategory;

  public UsageReportSummary(float meanUsagePerDay,
                            LocalDate mostPopularDayOfMonth,
                            LocalDate leastPopularDayOfMonth,
                            ParkingZone parkingZone,
                            ParkingCategory parkingCategory) {
    this.meanUsagePerDay = meanUsagePerDay;
    this.mostPopularDayOfMonth = Optional.ofNullable(mostPopularDayOfMonth);
    this.leastPopularDayOfMonth = Optional.ofNullable(leastPopularDayOfMonth);
    this.parkingZone = Optional.ofNullable(parkingZone);
    this.parkingCategory = Optional.ofNullable(parkingCategory);
  }

  public float getMeanUsagePerDay() {
    return meanUsagePerDay;
  }

  public Optional<LocalDate> getMostPopularDateOfMonth() {
    return mostPopularDayOfMonth;
  }

  public Optional<LocalDate> getLeastPopularDateOfMonth() {
    return leastPopularDayOfMonth;
  }

  public Optional<ParkingZone> getParkingZone() {
    return parkingZone;
  }

  public Optional<ParkingCategory> getParkingCategory() {
    return parkingCategory;
  }
}
