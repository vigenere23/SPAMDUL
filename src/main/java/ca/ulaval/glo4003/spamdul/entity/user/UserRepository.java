package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccess;
import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
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

  User findBy(BikeParkingAccessCode bikeParkingAccessCode);
}