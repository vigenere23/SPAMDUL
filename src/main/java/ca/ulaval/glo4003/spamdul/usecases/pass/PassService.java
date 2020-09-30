package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.*;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;

public class PassService {

  private PassRepository passRepository;
  private PassFactory passFactory;

  public PassService(PassRepository passRepository, PassFactory passFactory) {
    this.passRepository = passRepository;
    this.passFactory = passFactory;
  }

  public PassCode createPass(UserId userId, ParkingZone parkingZone, PassType passType) {
    Pass pass = passFactory.create(userId, parkingZone, passType);
    passRepository.save(pass);

    return pass.getPassCode();
  }
}
