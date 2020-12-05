package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongDayInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class DayOfWeekValidator extends PassValidator {

  private final Calendar calendar;
  private final UserFinderService userReader;

  public DayOfWeekValidator(Calendar calendar, UserFinderService userFinderService) {
    this.userReader = userFinderService;
    this.calendar = calendar;
  }

  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    PassCode passCode = PassCode.valueOf(passToValidateDto.passCode);
    User user = userReader.findBy(passCode);

    if (!user.canParkOnThisDayOfWeek(calendar.getDayOfWeek())) {

      throw new WrongDayInfractionException();
    }

    nextValidation(passToValidateDto);
  }

}
