package ca.ulaval.glo4003.spamdul.parking.entities.pass.bike;

import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;

public class BikeParkingAccessValidator {

  private final Calendar calendar;

  public BikeParkingAccessValidator(Calendar calendar) {
    this.calendar = calendar;
  }

  public boolean validate(BikeParkingPass bikeParkingPass) {
    return bikeParkingPass.canAccessBikeParking(calendar.now());
  }
}
