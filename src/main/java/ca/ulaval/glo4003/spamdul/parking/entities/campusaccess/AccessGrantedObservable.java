package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.entities.Observable;
import java.time.LocalDate;

public class AccessGrantedObservable extends Observable<AccessGrantedObserver> {

  public void notifyAccessGranted(ParkingZone parkingZone, LocalDate accessDate) {
    observers.forEach(observer -> observer.handleAccessGranted(parkingZone, accessDate));
  }
}
