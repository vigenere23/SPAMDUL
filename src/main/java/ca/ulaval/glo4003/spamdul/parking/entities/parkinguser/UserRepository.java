package ca.ulaval.glo4003.spamdul.parking.entities.parkinguser;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.InfractionId;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;

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
