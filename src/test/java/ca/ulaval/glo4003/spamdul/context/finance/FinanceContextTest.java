package ca.ulaval.glo4003.spamdul.context.finance;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.finance.api.revenue.RevenueResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class FinanceContextTest {

  private FinanceContext context;
  private InstanceMap resources;

  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new FinanceContext(authenticationRepository, cookieAssembler);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     RevenueResource.class);
  }
}
