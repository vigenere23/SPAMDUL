package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import java.time.LocalDate;
import java.util.List;

public interface ParkingAccessLogFilter {
    public ParkingAccessLogFilter setData(List<ParkingAccessLog> accessLogs);
    public ParkingAccessLogFilter fromCurrentMonth();
    public ParkingAccessLogFilter atZone(ParkingZone zone);
    public ParkingAccessLogFilter atDate(LocalDate date);
    public ParkingAccessLogFilter betweenDates(LocalDate startDate, LocalDate endDate);
    public List<ParkingAccessLog> getResults();
    public List<ParkingAccessLog> getResultsAndReset();
}
