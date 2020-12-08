package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmptyParkingParkingPassCodeValidatorTest {

  public static final String A_NOT_EMPTY_PASS_CODE = "dssd";
  private EmptyCarParkingPassCodeValidator emptyPassCodeValidator = new EmptyCarParkingPassCodeValidator();

  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @After
  public void clearStatic() {
    CarParkingPassValidator.setPassRepository(null);
    CarParkingPassValidator.passCache.clear();
  }

  @Test
  public void givenEmptyPassCodeString_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = "";

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_03");

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
