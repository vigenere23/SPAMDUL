package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;

public interface AccessGrantedObserver {

  void handleAccessGrantedWithCampusAccess(ParkingZone parkingZone, LocalDate accessDate);
}
