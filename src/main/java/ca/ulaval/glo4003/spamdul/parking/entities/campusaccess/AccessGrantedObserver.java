package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;

public interface AccessGrantedObserver {

  void handleAccessGranted(ParkingZone parkingZone, LocalDate accessDate);
}
