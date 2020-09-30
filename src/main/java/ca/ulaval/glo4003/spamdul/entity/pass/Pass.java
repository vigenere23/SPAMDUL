package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.user.UserId;


public class Pass {

  private PassCode passCode;
  private UserId userId;
  private ParkingZone parkingZone;
  private PassType passType;

  public Pass(PassCode passCode, UserId userId, ParkingZone parkingZone, PassType passType) {
    this.passCode = passCode;
    this.userId = userId;
    this.parkingZone = parkingZone;
    this.passType = passType;
  }

  public PassCode getPassCode() {
    return passCode;
  }

  public UserId getUserId() {
    return userId;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public PassType getPassType() {
    return passType;
  }
}
