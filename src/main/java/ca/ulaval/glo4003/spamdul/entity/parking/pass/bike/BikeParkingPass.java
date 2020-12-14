package ca.ulaval.glo4003.spamdul.entity.parking.pass.bike;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;
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
