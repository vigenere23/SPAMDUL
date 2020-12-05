package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
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
  private UserRepository userRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private User user;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    PassValidator.setPassRepository(userRepository);
  }

  @After
  public void clearStatic() {
    PassValidator.setPassRepository(null);
    PassValidator.userCache.clear();
  }

  @Test
  public void givenNoPassInRepo_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenThrow(UserNotFoundException.class);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_02");

    passExistsValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    passExistsValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(any(PassCode.class))).thenReturn(user);

    passExistsValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
