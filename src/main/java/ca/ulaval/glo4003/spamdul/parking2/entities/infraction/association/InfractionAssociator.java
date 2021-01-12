package ca.ulaval.glo4003.spamdul.parking2.entities.infraction.association;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUser;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserRepository;

public class InfractionAssociator {

  private final ParkingUserRepository parkingUserRepository;

  public InfractionAssociator(ParkingUserRepository parkingUserRepository) {
    this.parkingUserRepository = parkingUserRepository;
  }

  public void associateInfraction(AccountId accountId, Infraction infraction) {
    ParkingUser parkingUser = parkingUserRepository.findBy(accountId);
    parkingUser.addInfraction(infraction);
    parkingUserRepository.save(parkingUser);
  }
}
