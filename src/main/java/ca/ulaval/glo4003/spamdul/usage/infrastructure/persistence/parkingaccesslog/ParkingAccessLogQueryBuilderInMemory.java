package ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.filter.FilterContainer;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogQueryBuilder;

import java.time.LocalDate;
import java.util.List;

public class ParkingAccessLogQueryBuilderInMemory implements ParkingAccessLogQueryBuilder {

  private final FilterContainer<ParkingAccessLog> filterContainer = new FilterContainer<>();

  public ParkingAccessLogQueryBuilderInMemory(List<ParkingAccessLog> accessLogs) {
    filterContainer.setData(accessLogs);
  }

  public ParkingAccessLogQueryBuilderInMemory atCategory(ParkingCategory category) {
    if (category != null) {
      if (category == ParkingCategory.CAR) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() != ParkingZone.ZONE_BIKE);
      } else if (category == ParkingCategory.BIKE) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == ParkingZone.ZONE_BIKE);
      }
    }
    return this;
  }

  public ParkingAccessLogQueryBuilderInMemory atZone(ParkingZone zone) {
    if (zone != null) {
      filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == zone);
    }
    return this;
  }

  public ParkingAccessLogQueryBuilderInMemory atDate(LocalDate date) {
    if (date != null) {
      filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().isEqual(date));
    }
    return this;
  }

  public ParkingAccessLogQueryBuilderInMemory betweenDates(LocalDate startDate, LocalDate endDate) {
    if (startDate != null) {
      filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isBefore(startDate));
    }

    if (endDate != null) {
      filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isAfter(endDate));
    }

    return this;
  }

  public List<ParkingAccessLog> getAll() {
    return filterContainer.getResults();
  }
}
