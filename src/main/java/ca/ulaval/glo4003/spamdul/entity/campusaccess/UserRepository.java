package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public interface UserRepository {

  void save(User user);

  User findBy(UserId userId);

  User findBy(CampusAccessCode campusAccessCode);

  User findBy(PassCode passCode);

  User findBy(LicensePlate licensePlate);

  User findBy(InfractionId infractionId);

  User findBy(RechargULCardId rechargULCardId);
}