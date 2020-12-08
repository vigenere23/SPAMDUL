package ca.ulaval.glo4003.spamdul.entity.user;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;

public interface UserRepository {

  void save(User user);

  User findBy(UserId userId);

  User findBy(CampusAccessCode campusAccessCode);

  User findBy(CarParkingPassCode parkingPassCode);

  User findBy(LicensePlate licensePlate);

  User findBy(InfractionId infractionId);

  User findBy(RechargULCardId rechargULCardId);

  User findBy(BikeParkingPassCode bikeParkingPassCode);
}