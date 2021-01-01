package ca.ulaval.glo4003.spamdul.authentication.entities.accesslevelvalidator;

import static org.mockito.BDDMockito.given;

import ca.ulaval.glo4003.spamdul.authentication.entities.AccessLevel;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.authentication.entities.RegisteredUser;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.authentication.entities.UnauthorizedUserException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportAccessLevelValidatorTest {

  public static final TemporaryToken A_TEMPORARY_TOKEN = new TemporaryToken();
  private UsageReportAccessLevelValidator validator;
  private RegisteredUser registeredUserWithRightAccess;
  private RegisteredUser registeredUserWithWrongAccess;

  @Mock
  private AuthenticationRepository authenticationRepository;

  @Before
  public void setUp() throws Exception {
    validator = new UsageReportAccessLevelValidator(authenticationRepository);
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
}
