package ca.ulaval.glo4003.spamdul.parking.context.infractions;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InfractionTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
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
                                     InfractionResource.class);
  }
}
