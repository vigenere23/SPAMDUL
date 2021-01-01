package ca.ulaval.glo4003.spamdul.parking.entities.pass;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

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

  public boolean doesBoundInstant(LocalDateTime now) {
    return timePeriod.bounds(now);
  }

  public boolean isValidOnThisDayOfWeek(DayOfWeek dayOfWeek) {
    return timePeriod.mayIncludeThisDayOfWeek(dayOfWeek);
  }
}
