package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.utils.Observable;
import java.time.LocalDate;

public class AccessGrantedObservable extends Observable<AccessGrantedObserver> {

  public void notifyAccessGrantedWithCampusAccess(CampusAccess campusAccess, LocalDate accessDate) {
    observers.forEach(observer -> observer.handleAccessGrantedWithCampusAccess(campusAccess, accessDate));
  }
}
