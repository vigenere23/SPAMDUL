package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.NoPassInfractionException;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmptyParkingParkingPassCodeValidatorTest {

  public static final String A_NOT_EMPTY_PASS_CODE = "dssd";
  private EmptyCarParkingPassCodeValidator emptyPassCodeValidator = new EmptyCarParkingPassCodeValidator();

  private PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Test(expected = NoPassInfractionException.class)
  public void givenEmptyPassCodeString_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = "";

    emptyPassCodeValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    emptyPassCodeValidator.setNextValidator(nextCarParkingPassValidator);

    passToValidateDto.passCode = A_NOT_EMPTY_PASS_CODE;

    emptyPassCodeValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
