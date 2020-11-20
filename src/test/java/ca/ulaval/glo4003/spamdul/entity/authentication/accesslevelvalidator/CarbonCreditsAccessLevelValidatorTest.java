package ca.ulaval.glo4003.spamdul.entity.authentication.accesslevelvalidator;

import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.spamdul.entity.authentication.AccessLevel;
import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.authentication.NoRegisteredUserLoggedInException;
import ca.ulaval.glo4003.spamdul.entity.authentication.RegisteredUser;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usecases.infraction.UnauthorizedUserException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsAccessLevelValidatorTest {
  public static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();
  private CarbonCreditsAccessLevelValidator validator;
  private RegisteredUser registeredUserWithRightAccess;
  private RegisteredUser registeredUserWithWrongAccess;

  @Mock
  private AuthenticationRepository authenticationRepository;

  @Before
  public void setUp() throws Exception {
    validator = new CarbonCreditsAccessLevelValidator(authenticationRepository);
    registeredUserWithRightAccess = new RegisteredUser("username", AccessLevel.ADMIN);
    registeredUserWithWrongAccess = new RegisteredUser("username", AccessLevel.SSP_AGENT);
  }

  @Test
  public void givenTheRightAccessLevel_whenValidating_shouldNotThrowException() {
    given(authenticationRepository.findBy(A_TEMPORARY_TOKEN)).willReturn(registeredUserWithRightAccess);

    validator.validate(A_TEMPORARY_TOKEN);
  }

  @Test(expected = UnauthorizedUserException.class)
  public void givenTheWrongAccessLevel_whenValidating_shouldThrowException() {
    given(authenticationRepository.findBy(A_TEMPORARY_TOKEN)).willReturn(registeredUserWithWrongAccess);

    validator.validate(A_TEMPORARY_TOKEN);
  }

//  @Test(expected = UnauthorizedUserException.class)
//  public void givenNoRegisteredCorrespondingToToken_whenValidating_shouldThrowException() {
//    given(authenticationRepository.findBy(A_TEMPORARY_TOKEN)).willThrow(new NoRegisteredUserLoggedInException());
//
//    validator.validate(A_TEMPORARY_TOKEN);
//  }
}