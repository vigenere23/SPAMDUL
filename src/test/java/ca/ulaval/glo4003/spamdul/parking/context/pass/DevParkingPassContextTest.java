package ca.ulaval.glo4003.spamdul.parking.context.pass;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.PassTransactionService;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DevParkingPassContextTest {

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
                                     ParkingPassResource.class);
  }
}
