package ca.ulaval.glo4003.spamdul.context.authentication;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.authentication.api.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationContextTest {

  private AuthenticationContext context;
  private InstanceMap resources;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new AuthenticationContext();
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     AuthenticationResource.class);
  }
}
