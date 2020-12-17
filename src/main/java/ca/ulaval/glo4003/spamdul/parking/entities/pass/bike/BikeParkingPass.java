package ca.ulaval.glo4003.spamdul.parking.entities.pass.bike;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import java.time.LocalDateTime;

public class BikeParkingPass extends ParkingPass {

  public BikeParkingPass(ParkingPassCode bikeParkingPassCode, TimePeriod timePeriod) {
    super(bikeParkingPassCode, ParkingZone.ZONE_BIKE, timePeriod);
  }

  @Override
  public void accept(User user) {
    user.associateBikeParkingPass(this);
  }

  public boolean canAccessBikeParking(LocalDateTime now) {
    return getTimePeriod().bounds(now);
  }
}
