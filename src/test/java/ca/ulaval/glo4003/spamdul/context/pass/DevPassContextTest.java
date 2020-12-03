package ca.ulaval.glo4003.spamdul.context.pass;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.PassExceptionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DevPassContextTest {

  private DevPassContext context;
  private InstanceMap resources;

  @Mock
  private PassTransactionService passTransactionService;
  @Mock
  private UserRepository userRepository;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevPassContext(passTransactionService, userRepository);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     PassResource.class,
                                     PassExceptionAssembler.class,
                                     DeliveryExceptionAssembler.class);
  }
}
