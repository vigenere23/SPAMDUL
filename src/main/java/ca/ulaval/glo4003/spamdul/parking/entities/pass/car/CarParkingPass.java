package ca.ulaval.glo4003.spamdul.parking.entities.pass.car;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;

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
