package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.AccessGrantedObserver;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ParkingAccessLogger implements AccessGrantedObserver {

  private final Logger logger = Logger.getLogger(getClass().getName());
  private final ParkingAccessLogFactory parkingAccessLogFactory;
  private final ParkingAccessLogRepository parkingAccessLogRepository;

  public ParkingAccessLogger(ParkingAccessLogFactory parkingAccessLogFactory,
                             ParkingAccessLogRepository parkingAccessLogRepository) {
    this.parkingAccessLogFactory = parkingAccessLogFactory;
    this.parkingAccessLogRepository = parkingAccessLogRepository;
  }

  @Override public void handleAccessGranted(ParkingZone parkingZone, LocalDate accessDate) {
    parkingAccessLogRepository.save(parkingAccessLogFactory.create(parkingZone, accessDate));
    logger.info("Creating and saving parkingAccessLogFactory");
  }
}
