package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.Observable;
import java.time.LocalDate;

public class AccessGrantedObservable extends Observable<AccessGrantedObserver> {

  public void notifyAccessGrantedWithCampusAccess(ParkingZone parkingZone, LocalDate accessDate) {
    observers.forEach(observer -> observer.handleAccessGrantedWithCampusAccess(parkingZone, accessDate));
  }
}
