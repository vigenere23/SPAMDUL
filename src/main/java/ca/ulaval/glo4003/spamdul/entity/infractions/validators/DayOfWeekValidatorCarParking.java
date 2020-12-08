package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

public class DayOfWeekValidatorCarParking extends CarParkingPassValidator {

  private final Calendar calendar;

  public DayOfWeekValidatorCarParking(Calendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    ParkingPass parkingPass = getCorrespondingPass(CarParkingPassCode.valueOf(passToValidateDto.passCode));

    if (!parkingPass.getTimePeriod().getTimePeriodDayOfWeek().include(calendar.getDayOfWeek())) {

      throw new InfractionException("VIG_01");
    }

    nextValidation(passToValidateDto);
  }
}
