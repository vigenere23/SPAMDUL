package ca.ulaval.glo4003.spamdul.context.usagereport;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportExceptionAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DevUsageReportContextTest {

  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;

  private DevUsageReportContext devUsageReportContext;
  private InstanceMap resources;

  @Before
  public void setUp() {
    devUsageReportContext = new DevUsageReportContext(authenticationRepository, cookieAssembler);
    resources = new InstanceMap();
  }

  @Test
  public void whenRegisteringResources_shouldAddAllResources() {
    devUsageReportContext.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     UsageReportResource.class,
                                     UsageReportExceptionAssembler.class);
  }
}
