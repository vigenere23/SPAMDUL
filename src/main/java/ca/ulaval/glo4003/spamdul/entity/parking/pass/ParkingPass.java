package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public abstract class ParkingPass {

  private final ParkingPassCode parkingPassCode;
  private final ParkingZone parkingZone;
  private final TimePeriod timePeriod;

  public ParkingPass(ParkingPassCode parkingPassCode, ParkingZone parkingZone, TimePeriod timePeriod) {
    this.parkingPassCode = parkingPassCode;
    this.parkingZone = parkingZone;
    this.timePeriod = timePeriod;
  }

  public boolean isAValidParkingZone(ParkingZone parkingZone) {
    return this.parkingZone.equals(parkingZone);
  }

  public ParkingPassCode getCode() {
    return parkingPassCode;
  }

  public ParkingZone getParkingZone() {
    return parkingZone;
  }

  public TimePeriod getTimePeriod() {
    return timePeriod;
  }

  public abstract void accept(User user);
}
