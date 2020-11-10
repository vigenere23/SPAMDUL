package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;

public interface CampusAccessRepository {

  CampusAccessCode save(CampusAccess campusAccess);

  CampusAccess findBy(CampusAccessCode campusAccessCode);

  CampusAccess findBy(LicensePlate licensePlate);
}
