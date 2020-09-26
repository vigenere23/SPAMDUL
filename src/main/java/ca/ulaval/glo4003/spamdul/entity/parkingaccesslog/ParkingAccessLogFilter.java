package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.utils.FilterContainer;

import java.time.LocalDate;
import java.util.List;

public class ParkingAccessLogFilter {
    private FilterContainer<ParkingAccessLog> filterContainer;

    public ParkingAccessLogFilter setData(List<ParkingAccessLog> accessLogs) {
        filterContainer = new FilterContainer<>(accessLogs);
        return this;
    }

    public ParkingAccessLogFilter fromOngoingMonth() {
        LocalDate now = LocalDate.now();
        filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isAfter(now));
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().getMonth() == now.getMonth());
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().getYear() == now.getYear());
        return this;
    }

    public ParkingAccessLogFilter atZone(ParkingZone zone) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getZone() == zone);
        return this;
    }

    public ParkingAccessLogFilter atDate(LocalDate date) {
        filterContainer.addFilter(parkingAccessLog -> parkingAccessLog.getAccessDate().isEqual(date));
        return this;
    }

    public ParkingAccessLogFilter betweenDates(LocalDate startDate, LocalDate endDate) {
        filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isBefore(startDate));
        filterContainer.addFilter(parkingAccessLog -> !parkingAccessLog.getAccessDate().isAfter(endDate));
        return this;
    }

    public List<ParkingAccessLog> getResults() {
        return filterContainer.getResults();
    }
}
