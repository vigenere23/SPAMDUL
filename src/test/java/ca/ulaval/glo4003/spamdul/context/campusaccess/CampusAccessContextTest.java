package ca.ulaval.glo4003.spamdul.context.campusaccess;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.CampusAccessResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class CampusAccessContextTest {

  private CampusAccessContext context;
  private InstanceMap resources;

  @Mock
  private ParkingAccessLogger parkingAccessLogger;
  @Mock
  private CampusAccessTransactionService campusAccessTransactionService;
  @Mock
  private UserRepository userRepository;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new CampusAccessContext(userRepository, parkingAccessLogger, campusAccessTransactionService);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     CampusAccessResource.class);
  }
}
