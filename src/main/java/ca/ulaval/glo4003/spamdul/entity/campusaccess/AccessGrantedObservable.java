package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.utils.Observable;
import java.time.LocalDate;

public class AccessGrantedObservable extends Observable<AccessGrantedObserver> {

  public void notifyAccessGrantedWithCampusAccess(ParkingZone parkingZone, LocalDate accessDate) {
    observers.forEach(observer -> observer.handleAccessGrantedWithCampusAccess(parkingZone, accessDate));
  }
}
