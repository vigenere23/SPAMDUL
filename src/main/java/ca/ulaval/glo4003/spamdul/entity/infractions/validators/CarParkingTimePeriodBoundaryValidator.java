package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.User;

public class CarParkingTimePeriodBoundaryValidator extends CarParkingPassValidator {

  private final Calendar calendar;
  private final UserFinderService userReader;

  public CarParkingTimePeriodBoundaryValidator(Calendar calendar, UserFinderService userFinderService) {
    this.userReader = userFinderService;
    this.calendar = calendar;
  }


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
    CarParkingPassCode passCode = CarParkingPassCode.valueOf(passToValidateDto.passCode);
    User user = userReader.findBy(passCode);

    if (!user.hasParkingPassBoundingInstant(calendar.now())) {

      throw new InvalidPassInfractionException();
    }

    nextValidation(passToValidateDto);
  }

}
