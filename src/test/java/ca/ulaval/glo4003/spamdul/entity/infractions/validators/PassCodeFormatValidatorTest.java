package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PassCodeFormatValidatorTest {

  public static final String A_VALID_PASS_CODE = "12";
  private PassCodeFormatValidator passCodeFormatValidator = new PassCodeFormatValidator();

  private PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Test(expected = InvalidPassInfractionException.class)
  public void givenInvalidPassCodeString_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = "gt";

    passCodeFormatValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    passCodeFormatValidator.setNextValidator(nextPassValidator);

    passToValidateDto.passCode = A_VALID_PASS_CODE;

    passCodeFormatValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
