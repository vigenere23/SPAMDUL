package ca.ulaval.glo4003.spamdul.usecases.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogFilter;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParkingAccessLogFilterImpl implements ParkingAccessLogFilter {
    private Stream<ParkingAccessLog> filterStream = Stream.empty();

    public ParkingAccessLogFilter setData(List<ParkingAccessLog> accessLogs) {
        filterStream = accessLogs.stream();
        return this;
    }

    public ParkingAccessLogFilter fromCurrentMonth() {
        LocalDate now = LocalDate.now();
        filterStream = filterStream
                .filter(parkingAccessLog -> parkingAccessLog.getAccessDate().getMonth() == now.getMonth())
                .filter(parkingAccessLog -> parkingAccessLog.getAccessDate().getYear() == now.getYear());
        return this;
    }

    public ParkingAccessLogFilter atZone(ParkingZone zone) {
        filterStream = filterStream.filter(parkingAccessLog -> parkingAccessLog.getZone() == zone);
        return this;
    }

    public ParkingAccessLogFilter atDate(LocalDate date) {
        filterStream = filterStream.filter(parkingAccessLog -> parkingAccessLog.getAccessDate().isEqual(date));
        return this;
    }

    public ParkingAccessLogFilter betweenDates(LocalDate startDate, LocalDate endDate) {
        filterStream = filterStream
                .filter(parkingAccessLog -> parkingAccessLog.getAccessDate().isAfter(startDate))
                .filter(parkingAccessLog -> parkingAccessLog.getAccessDate().isBefore(endDate));
        return this;
    }

    public List<ParkingAccessLog> getResults() {
        return filterStream.collect(Collectors.toList());
    }

    public List<ParkingAccessLog> getResultsAndReset() {
        List<ParkingAccessLog> results = filterStream.collect(Collectors.toList());
        filterStream = Stream.empty();
        return results;
    }
}
