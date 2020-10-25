package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassExistsValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  private PassExistsValidator passExistsValidator = new PassExistsValidator();
  @Mock
  private PassRepository passRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    PassValidator.setPassRepository(passRepository);
  }

  @After
  public void clearStatic() {
    PassValidator.setPassRepository(null);
    PassValidator.passCache.clear();
  }

  @Test
  public void givenNoPassInRepo_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(passRepository.findByPassCode(passCode)).thenThrow(PassNotFoundException.class);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_02");

    passExistsValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    passExistsValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;

    passExistsValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
