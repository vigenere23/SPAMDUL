package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.NoPassInfractionException;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EmptyPassCodeValidatorTest {

  public static final String A_NOT_EMPTY_PASS_CODE = "dssd";
  private EmptyPassCodeValidator emptyPassCodeValidator = new EmptyPassCodeValidator();

  private PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Test(expected = NoPassInfractionException.class)
  public void givenEmptyPassCodeString_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = "";

    emptyPassCodeValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    emptyPassCodeValidator.setNextValidator(nextPassValidator);

    passToValidateDto.passCode = A_NOT_EMPTY_PASS_CODE;

    emptyPassCodeValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
