package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.exceptions.ParkingUserNotFound;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingUserRepositoryInMemory implements ParkingUserRepository {

  private final Map<ParkingUserId, ParkingUser> users = new HashMap<>();

  @Override
  public ParkingUser findBy(ParkingUserId id) {
    Optional<ParkingUser> parkingUser = Optional.ofNullable(users.get(id));

    return validateParkingUser(parkingUser);
  }

  @Override
  public ParkingUser findBy(LicensePlate licensePlate) {
    Optional<ParkingUser> parkingUser = users
        .values()
        .stream()
        .filter(user -> user.hasPermitWith(licensePlate))
        .findFirst();

    return validateParkingUser(parkingUser);
  }

  @Override
  public ParkingUser findBy(PermitNumber permitNumber) {
    Optional<ParkingUser> parkingUser = users
        .values()
        .stream()
        .filter(user -> user.hasPermitWith(permitNumber))
        .findFirst();

    return validateParkingUser(parkingUser);
  }

  private ParkingUser validateParkingUser(Optional<ParkingUser> parkingUser) {
    if (!parkingUser.isPresent()) {
      throw new ParkingUserNotFound();
    }

    return parkingUser.get();
  }

  @Override
  public void save(ParkingUser parkingUser) {
    users.put(parkingUser.getId(), parkingUser);
  }
}
