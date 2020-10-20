package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalTime;

public class ValidationChain {

  private PassValidator baseValidator;

  public ValidationChain(PassValidator baseValidator) {
    this.baseValidator = baseValidator;
  }

  public void validate(Pass pass, ParkingZone parkingZone, LocalTime time) {
    baseValidator.validate(pass, parkingZone, time);
  }
}
