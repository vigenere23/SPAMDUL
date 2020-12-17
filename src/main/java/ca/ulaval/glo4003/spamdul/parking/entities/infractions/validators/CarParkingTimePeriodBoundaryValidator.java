package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;

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
