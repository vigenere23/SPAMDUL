package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;

public class DayOfWeekValidator extends PassValidator {

  private final Calendar calendar;

  public DayOfWeekValidator(Calendar calendar) {
    this.calendar = calendar;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    Pass pass = getCorrespondingPass(PassCode.valueOf(passToValidateDto.passCode));

    if (!pass.getTimePeriod().getTimePeriodDayOfWeek().include(calendar.getDayOfWeek())) {

      throw new InfractionException("VIG_01");
    }

    nextValidation(passToValidateDto);
  }
}
