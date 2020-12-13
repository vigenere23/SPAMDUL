package ca.ulaval.glo4003.spamdul.context.fundraising;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.ui.fundraising.FundraisingResource;
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
                                     FundraisingResource.class, InitiativeExceptionMapper.class);
  }
}
