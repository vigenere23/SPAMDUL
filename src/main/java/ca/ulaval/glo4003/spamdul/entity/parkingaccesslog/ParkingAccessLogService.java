package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogAssembler;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;
import java.util.List;

public class ParkingAccessLogService {
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private ParkingAccessLogAssembler parkingAccessLogAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogAssembler parkingAccessLogAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogAssembler = parkingAccessLogAssembler;
  }

  public List<ParkingAccessLog> findParkingAccessesByDate(ParkingZone zone, LocalDate date) {
    return parkingAccessLogRepository
            .findAll()
            .stream()
            .filter(access -> access.zone.equals(zone))
            .filter(access -> access.accessDate.equals(date))
            .collect(Collectors.toList());
  }

  public List<ParkingAccessLog> findParkingAccessByDateRange(Parking zone, LocalDate startDate, LocalDate endDate) {
    return parkingAccessLogRepository
            .findAll()
            .stream()
            .filter(access -> access.zone.equals(zone))
            .filter(access -> access.accessDate.equals(startDate))
            .collect(Collectors.toList());
  }

  public List<ParkingAccessLog> findAllParkingAccess() {
    return null;
  }
}
