package ca.ulaval.glo4003.spamdul.entity.campusaccess;

public interface CampusAccessRepository {

  CampusAccessCode save(CampusAccess campusAccess);

  CampusAccess findById(CampusAccessCode campusAccessCode);
}
