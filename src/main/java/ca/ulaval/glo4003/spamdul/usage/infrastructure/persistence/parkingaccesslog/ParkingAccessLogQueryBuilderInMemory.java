package ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.filter.Filter;
import ca.ulaval.glo4003.spamdul.shared.infrastructure.filter.FilterComparator;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogQueryBuilder;

import java.time.LocalDate;
import java.util.List;

public class ParkingAccessLogQueryBuilderInMemory implements ParkingAccessLogQueryBuilder {

  private final Filter<ParkingAccessLog> filter = new Filter<>();

  public   ParkingAccessLogQueryBuilderInMemory(List<ParkingAccessLog> accessLogs) {
    filter.setData(accessLogs);
  }

  @Override
  public ParkingAccessLogQueryBuilderInMemory withCategory(ParkingCategory category) {
    if (category != null) {
      if (category == ParkingCategory.CAR) {
        filter.addCondition(parkingAccessLog -> parkingAccessLog.getZone() != ParkingZone.ZONE_BIKE);
      } else if (category == ParkingCategory.BIKE) {
        filter.addCondition(parkingAccessLog -> parkingAccessLog.getZone() == ParkingZone.ZONE_BIKE);
      }
    }
    return this;
  }

  @Override
  public ParkingAccessLogQueryBuilderInMemory withZone(ParkingZone zone) {
    if (zone != null) {
      filter.addCondition(parkingAccessLog -> parkingAccessLog.getZone().equals(zone));
    }
    return this;
  }

  @Override
  public FilterComparator<ParkingAccessLog, LocalDate, ParkingAccessLogQueryBuilder> withDate() {
    return new FilterComparator<>(filter, ParkingAccessLog::getAccessDate, this);
  }

  @Override
  public List<ParkingAccessLog> getAll() {
    return filter.getResults();
  }
}
