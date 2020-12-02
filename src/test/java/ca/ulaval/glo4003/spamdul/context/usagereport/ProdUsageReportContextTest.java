package ca.ulaval.glo4003.spamdul.context.usagereport;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.authentication.AuthenticationRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportExceptionAssembler;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProdUsageReportContextTest {

  @Mock
  private AuthenticationRepository authenticationRepository;
  @Mock
  private AccessTokenCookieAssembler cookieAssembler;
  @Mock
  private Set<Object> resources;

  private ProdUsageReportContext prodUsageReportContext;

  @Before
  public void setUp() {
    prodUsageReportContext = new ProdUsageReportContext(authenticationRepository, cookieAssembler);
  }

  @Test
  public void whenRegisteringResources_shouldAddResource() {
    prodUsageReportContext.registerResources(resources);
    verify(resources, times(2)).add(any(UsageReportResource.class));
  }

  @Test
  public void whenRegisteringResources_shouldAddExceptionMapper() {
    prodUsageReportContext.registerResources(resources);
    verify(resources, times(2)).add(any(UsageReportExceptionAssembler.class));
  }
}
