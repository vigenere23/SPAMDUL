package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;

public class Pass {

  private final PassCode passCode;
  private final ParkingZone parkingZone;
  private final TimePeriod timePeriod;

  public Pass(PassCode passCode, ParkingZone parkingZone, TimePeriod timePeriod) {
    this.passCode = passCode;
    this.parkingZone = parkingZone;
    this.timePeriod = timePeriod;
  }

  public boolean isAValidParkingZone(ParkingZone parkingZone) {
    return this.parkingZone.equals(parkingZone);
  }

  public PassCode getCode() {
    return passCode;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }
}
