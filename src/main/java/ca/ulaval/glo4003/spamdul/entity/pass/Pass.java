package ca.ulaval.glo4003.spamdul.entity.pass;

import java.time.DayOfWeek;


public class Pass {

  private DayOfWeek dayOfWeek;
  private PassCode passCode;
  private ParkingZone parkingZone;
  private PassType passType;

  public Pass(PassCode passCode, ParkingZone parkingZone, PassType passType, DayOfWeek dayOfWeek) {
    this.passCode = passCode;
    this.parkingZone = parkingZone;
    this.passType = passType;
    this.dayOfWeek = dayOfWeek;
  }

  public PassCode getPassCode() {
    return passCode;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public PassType getPassType() {
    return passType;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }
}
