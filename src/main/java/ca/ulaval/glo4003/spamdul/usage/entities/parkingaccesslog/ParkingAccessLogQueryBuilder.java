package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;

import java.time.LocalDate;
import java.util.List;

public interface ParkingAccessLogQueryBuilder {
    ParkingAccessLogQueryBuilder atCategory(ParkingCategory category);

    ParkingAccessLogQueryBuilder atZone(ParkingZone zone);

    ParkingAccessLogQueryBuilder atDate(LocalDate date);

    ParkingAccessLogQueryBuilder betweenDates(LocalDate startDate, LocalDate endDate);

    List<ParkingAccessLog> getAll();
}
