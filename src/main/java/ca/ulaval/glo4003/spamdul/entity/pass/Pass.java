package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;

public class Pass {

  private final PassCode passCode;
  private ParkingZone parkingZone;
  private TimePeriod timePeriod;

  public Pass(PassCode passCode, ParkingZone parkingZone, TimePeriod timePeriod) {
    this.passCode = passCode;
    this.parkingZone = parkingZone;
    this.timePeriod = timePeriod;
  }

  public boolean isAValidParkingZone(ParkingZone parkingZone) {
    return this.parkingZone.equals(parkingZone);
  }

  public PassCode getPassCode() {
    return passCode;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }
}
