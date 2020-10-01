package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public interface CampusAccessRepository {

  CampusAccessCode save(CampusAccess campusAccess);

  CampusAccess findById(CampusAccessCode campusAccessCode);
}
