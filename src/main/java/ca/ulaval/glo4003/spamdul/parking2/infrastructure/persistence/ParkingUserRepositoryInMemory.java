package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.CarNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.ParkingUserNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.PermitNotFoundException;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingUserRepositoryInMemory implements ParkingUserRepository {

  private final Map<AccountId, ParkingUser> users = new HashMap<>();

  @Override
  public ParkingUser findBy(AccountId accountId) {
    return Optional.ofNullable(users.get(accountId)).orElseThrow(() -> new ParkingUserNotFoundException(accountId));
  }

  @Override
  public ParkingUser findBy(LicensePlate licensePlate) {
    return users
        .values()
        .stream()
        .filter(user -> user.hasPermitWith(licensePlate))
        .findFirst()
        .orElseThrow(() -> new CarNotFoundException(licensePlate));
  }

  @Override
  public ParkingUser findBy(PermitNumber permitNumber) {
    return users
        .values()
        .stream()
        .filter(user -> user.hasPermitWith(permitNumber))
        .findFirst()
        .orElseThrow(() -> new PermitNotFoundException(permitNumber));
  }

  @Override
  public void save(ParkingUser parkingUser) {
    users.put(parkingUser.getAccountId(), parkingUser);
  }
}
