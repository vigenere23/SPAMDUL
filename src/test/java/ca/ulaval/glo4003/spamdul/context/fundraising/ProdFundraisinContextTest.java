package ca.ulaval.glo4003.spamdul.context.fundraising;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.InitiativeTransactionService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ProdFundraisinContextTest {

  private ProdFundraisingContext context;
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
    context = new ProdFundraisingContext(initiativeTransactionService, authenticationRepository, cookieAssembler);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     FundraisingResource.class, InitiativeExceptionMapper.class);
  }
}