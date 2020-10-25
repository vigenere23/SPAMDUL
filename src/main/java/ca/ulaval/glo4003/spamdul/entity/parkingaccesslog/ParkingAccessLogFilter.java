package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.utils.filter.FilterContainer;
import java.time.LocalDate;
import java.util.List;

public class ParkingAccessLogFilter {

  private final FilterContainer<ParkingAccessLog> filterContainer = new FilterContainer<>();

  public ParkingAccessLogFilter setData(List<ParkingAccessLog> accessLogs) {
    filterContainer.setData(accessLogs);
    return this;
  }

  public ParkingAccessLogFilter atZone(ParkingZone zone) {
    if (zone == null) {
      return this;
    }

    filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == zone);
    return this;
  }

  public ParkingAccessLogFilter atDate(LocalDate date) {
    if (date == null) {
      return this;
    }

    filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().isEqual(date));
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
