package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.utils.filter.FilterContainer;
import java.time.LocalDate;
import java.util.List;

public class ParkingAccessLogFilter {

  private final FilterContainer<ParkingAccessLog> filterContainer = new FilterContainer<>();

  public ParkingAccessLogFilter setData(List<ParkingAccessLog> accessLogs) {
    filterContainer.setData(accessLogs);
    return this;
  }

  public ParkingAccessLogFilter atCategory(ParkingCategory category) {
    if (category != null) {
      if (category == ParkingCategory.CAR) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() != ParkingZone.ZONE_BIKE);
      } else if (category == ParkingCategory.BIKE) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == ParkingZone.ZONE_BIKE);
      }
    }
    return this;
  }

  public ParkingAccessLogFilter atZone(ParkingZone zone) {
    if (zone != null) {
      filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == zone);
    }
    return this;
  }

  public ParkingAccessLogFilter atDate(LocalDate date) {
    if (date != null) {
      filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().isEqual(date));
    }
    return this;
  }

  public ParkingAccessLogFilter betweenDates(LocalDate startDate, LocalDate endDate) {
    if (startDate != null) {
      filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isBefore(startDate));
    }

    if (endDate != null) {
      filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isAfter(endDate));
    }

    return this;
  }

  public List<ParkingAccessLog> getResults() {
    return filterContainer.getResults();
  }
}
