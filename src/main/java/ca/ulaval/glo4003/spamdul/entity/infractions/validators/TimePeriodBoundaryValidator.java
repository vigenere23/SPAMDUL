package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

public class TimePeriodBoundaryValidator extends PassValidator {

  private final Calendar calendar;

  public TimePeriodBoundaryValidator(Calendar calendar) {
    this.calendar = calendar;
  }


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    Pass pass = getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));

    if (!pass.getTimePeriod().bounds(calendar.now())) {

      throw new InfractionException("VIG_02");
    }

    nextValidation(passToValidateDto);
  }
}
