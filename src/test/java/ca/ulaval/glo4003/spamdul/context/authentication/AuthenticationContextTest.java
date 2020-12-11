package ca.ulaval.glo4003.spamdul.context.authentication;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.ui.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
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
