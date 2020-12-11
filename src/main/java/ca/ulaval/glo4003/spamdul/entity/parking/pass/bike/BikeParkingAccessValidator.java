package ca.ulaval.glo4003.spamdul.entity.parking.pass.bike;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

public class BikeParkingAccessValidator {


  private final Calendar calendar;

  public BikeParkingAccessValidator(Calendar calendar) {
    this.calendar = calendar;
  }

  public boolean validate(BikeParkingPass bikeParkingPass) {
    return bikeParkingPass.getTimePeriod().bounds(calendar.now());
  }
}
