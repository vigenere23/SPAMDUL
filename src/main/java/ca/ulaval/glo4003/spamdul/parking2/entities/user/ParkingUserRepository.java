package ca.ulaval.glo4003.spamdul.parking2.entities.user;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;

public interface ParkingUserRepository {

  ParkingUser findBy(AccountId accountId);

  ParkingUser findBy(LicensePlate licensePlate);

  ParkingUser findBy(PermitNumber permitNumber);

  void save(ParkingUser parkingUser);
}
