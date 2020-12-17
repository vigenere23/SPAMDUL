package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingPassExistsValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";

  private CarParkingPassExistsValidator passExistsValidator;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private UserFinderService userFinderService;
  @Mock
  private User user;

  @Before
  public void setUp() {
    passExistsValidator = new CarParkingPassExistsValidator(userFinderService);
  }

  @Test(expected = InvalidPassInfractionException.class)
  public void givenNoPassInRepo_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    CarParkingPassCode passCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenThrow(UserNotFoundException.class);

    passExistsValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    passExistsValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userFinderService.findBy(any(CarParkingPassCode.class))).thenReturn(user);

    passExistsValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
