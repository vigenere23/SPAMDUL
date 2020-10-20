package ca.ulaval.glo4003.spamdul.entity.infractions;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import java.time.LocalTime;

public abstract class PassValidator {

  protected PassValidator nextPassValidator;

  public void setNextValidator(PassValidator passValidator) {
    nextPassValidator = passValidator;
  }

  public void validate(Pass pass, ParkingZone parkingZone, LocalTime time) {
    if (nextPassValidator != null) {
      nextPassValidator.validate(pass, parkingZone, time);
    }
  }
}
