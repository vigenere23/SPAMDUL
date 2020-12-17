package ca.ulaval.glo4003.spamdul.context.pass;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.api.pass.ParkingPassResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ProdParkingPassContextTest {

  private ProdPassContext context;
  private InstanceMap resources;

  @Mock
  private PassTransactionService passTransactionService;
  @Mock
  UserRepository userRepository;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new ProdPassContext(passTransactionService, userRepository);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     ParkingPassResource.class);
  }
}
