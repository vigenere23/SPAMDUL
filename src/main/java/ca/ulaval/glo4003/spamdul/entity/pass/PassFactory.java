package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class PassFactory {

  public PassFactory() {
  }

  public Pass create(UserId userId, ParkingZone parkingZone, PassType passType) {
    PassCode passCode = new PassCode();

    return new Pass(passCode, userId, parkingZone, passType);
  }
}
