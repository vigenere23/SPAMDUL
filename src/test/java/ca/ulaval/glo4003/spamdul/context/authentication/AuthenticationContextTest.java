package ca.ulaval.glo4003.spamdul.context.authentication;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.authentification.AuthenticationResource;
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
                                     AuthenticationResource.class,
                                     AuthenticationExceptionAssembler.class,
                                     UserExceptionAssembler.class);
  }
}
