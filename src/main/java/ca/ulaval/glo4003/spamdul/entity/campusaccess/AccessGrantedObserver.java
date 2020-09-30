package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import java.time.LocalDate;

public interface AccessGrantedObserver {

  void handleAccessGrantedWithCampusAccess(ParkingZone parkingZone, LocalDate accessDate);
}
