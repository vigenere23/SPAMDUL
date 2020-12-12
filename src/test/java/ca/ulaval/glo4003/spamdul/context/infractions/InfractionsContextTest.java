package ca.ulaval.glo4003.spamdul.context.infractions;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionExceptionAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class InfractionsContextTest {

  private InfractionsContext context;
  private InstanceMap resources;

  @Mock
  private UserRepository userRepository;
  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;
  @Mock
  private InfractionTransactionService infractionTransactionService;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new InfractionsContext(authenticationRepository,
                                     userRepository,
                                     cookieAssembler,
                                     infractionTransactionService);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     InfractionResource.class, InfractionExceptionAssembler.class);
  }
}
