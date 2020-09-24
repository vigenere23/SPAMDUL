package ca.ulaval.glo4003.spamdul.usecases.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parkingaccesslog.ParkingAccessLogAssembler;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingAccessLogService {
  private ParkingAccessLogRepository parkingAccessLogRepository;
  private ParkingAccessLogAssembler parkingAccessLogAssembler;

  public ParkingAccessLogService(ParkingAccessLogRepository parkingAccessLogRepository,
                                 ParkingAccessLogAssembler parkingAccessLogAssembler) {
    this.parkingAccessLogRepository = parkingAccessLogRepository;
    this.parkingAccessLogAssembler = parkingAccessLogAssembler;
  }

  public List<ParkingAccessLogDto> findParkingAccessesByDate(ParkingZone zone, LocalDate date) {
    return parkingAccessLogRepository
            .findAll()
            .stream()
            .filter(access -> access.getZone().equals(zone))
            .filter(access -> access.getAccessDate().equals(date))
            .map(parkingAccessLogAssembler::toDto) // TODO really wanted here?
            .collect(Collectors.toList());
  }

  public List<ParkingAccessLogDto> findParkingAccessByDateRange(ParkingZone zone, LocalDate startDate, LocalDate endDate) {
    return parkingAccessLogRepository
            .findAll()
            .stream()
            .filter(access -> access.getZone().equals(zone))
            .filter(access -> access.getAccessDate().equals(startDate))
            .map(parkingAccessLogAssembler::toDto)
            .collect(Collectors.toList());
  }

  public List<ParkingAccessLog> findAllParkingAccess() {
    return null;
  }
}
