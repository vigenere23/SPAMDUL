package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.utils.Observable;
import java.time.LocalDate;

public class AccessGrantedObservable extends Observable<AccessGrantedObserver> {

  public void notifyAccessGranted(ParkingZone parkingZone, LocalDate accessDate) {
    observers.forEach(observer -> observer.handleAccessGranted(parkingZone, accessDate));
  }
}
