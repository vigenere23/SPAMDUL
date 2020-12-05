package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InvalidPassInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PassExistsValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  private PassExistsValidator passExistsValidator;

  @Mock
  private UserFinderService userFinderService;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private User user;

  @Before
  public void setUp() {
    passExistsValidator = new PassExistsValidator(userFinderService);
  }

  @Test(expected = InvalidPassInfractionException.class)
  public void givenNoPassInRepo_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenThrow(UserNotFoundException.class);

    passExistsValidator.validate(passToValidateDto);
  }

  @Test
  public void givenNotEmptyPassCodeString_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    passExistsValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userFinderService.findBy(any(PassCode.class))).thenReturn(user);

    passExistsValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
