package ca.ulaval.glo4003.spamdul.context.fundraising;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.FundraisingResource;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DevFundraisingContextTest {

  private DevFundraisingContext context;
  private InstanceMap resources;

  @Mock
  private InitiativeTransactionService initiativeTransactionService;
  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevFundraisingContext(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     FundraisingResource.class);
  }
}
