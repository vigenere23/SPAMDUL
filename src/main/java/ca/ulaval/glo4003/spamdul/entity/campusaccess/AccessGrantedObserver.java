package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import java.time.LocalDate;

public interface AccessGrantedObserver {

  void handleAccessGrantedWithCampusAccess(CampusAccess campusAccess, LocalDate accessDate);
}
