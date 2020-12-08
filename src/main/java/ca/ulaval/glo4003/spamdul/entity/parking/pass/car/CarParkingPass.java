package ca.ulaval.glo4003.spamdul.entity.parking.pass.car;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class CarParkingPass extends ParkingPass {

  public CarParkingPass(ParkingPassCode parkingPassCode,
                        ParkingZone parkingZone,
                        TimePeriod timePeriod) {
    super(parkingPassCode, parkingZone, timePeriod);
  }

  @Override
  public void accept(User user) {
    user.associateCarParkingPass(this);
  }


}
