package ca.ulaval.glo4003.spamdul.parking2.infrastructure.persistence;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitNumber;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserNotFound;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ParkingUserRepositoryInMemory implements ParkingUserRepository {

  private final Set<ParkingUser> users = new HashSet<>();

  @Override
  public ParkingUser findBy(LicensePlate licensePlate) {
    Optional<ParkingUser> parkingUser = users.stream().filter(user -> user.hasCarWith(licensePlate)).findFirst();

    return validateParkingUser(parkingUser);
  }

  @Override
  public ParkingUser findBy(PermitNumber permitNumber) {
    Optional<ParkingUser> parkingUser = users.stream().filter(user -> user.hasCarWith(permitNumber)).findFirst();

    return validateParkingUser(parkingUser);
  }

  private ParkingUser validateParkingUser(Optional<ParkingUser> parkingUser) {
    if (!parkingUser.isPresent()) {
      throw new ParkingUserNotFound();
    }

    return parkingUser.get();
  }

  @Override
  public void add(ParkingUser parkingUser) {
    users.add(parkingUser);
  }
}
