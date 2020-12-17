package ca.ulaval.glo4003.spamdul.context.campusaccess;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.finance.entities.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogger;
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
