package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

public class TimePeriodBoundaryValidatorCarParking extends CarParkingPassValidator {

  private final Calendar calendar;

  public TimePeriodBoundaryValidatorCarParking(Calendar calendar) {
    this.calendar = calendar;
  }


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    CarParkingPass parkingPass = getCorrespondingPass(CarParkingPassCode.valueOf(passToValidateDto.passCode));

    if (!parkingPass.getTimePeriod().bounds(calendar.now())) {

      throw new InfractionException("VIG_02");
    }

    nextValidation(passToValidateDto);
  }
}
