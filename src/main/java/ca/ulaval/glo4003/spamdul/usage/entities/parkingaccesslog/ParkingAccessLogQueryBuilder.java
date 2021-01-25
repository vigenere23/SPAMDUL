package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.filter.FilterComparator;

import java.time.LocalDate;
import java.util.List;

public interface ParkingAccessLogQueryBuilder {
    ParkingAccessLogQueryBuilder withCategory(ParkingCategory category);

    ParkingAccessLogQueryBuilder withZone(ParkingZone zone);

    FilterComparator<ParkingAccessLog, LocalDate, ParkingAccessLogQueryBuilder> withDate();

    List<ParkingAccessLog> getAll();
}
