package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.AccessGrantedObserver;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;
import java.util.logging.Logger;

public class ParkingAccessLogger implements AccessGrantedObserver {

  private final Logger logger = Logger.getLogger(getClass().getName()); // TODO remove once not needed
  private final ParkingAccessLogFactory parkingAccessLogFactory;
  private final ParkingAccessLogRepository parkingAccessLogRepository;

  public ParkingAccessLogger(ParkingAccessLogFactory parkingAccessLogFactory,
                             ParkingAccessLogRepository parkingAccessLogRepository) {
    this.parkingAccessLogFactory = parkingAccessLogFactory;
    this.parkingAccessLogRepository = parkingAccessLogRepository;
  }

  @Override public void handleAccessGrantedWithCampusAccess(ParkingZone parkingZone, LocalDate accessDate) {
    // TODO need parking zone!
    // TODO could add carId to ParkingAccessLog fields
    parkingAccessLogRepository.save(parkingAccessLogFactory.create(parkingZone, accessDate));
    logger.info("Creating and saving parkingAccessLogFactory");
  }
}
