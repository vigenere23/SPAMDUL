package ca.ulaval.glo4003.spamdul.context.campusaccess;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction_services.CampusAccessTransactionService;
import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingAccessLogger;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
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

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new CampusAccessContext(parkingAccessLogger, campusAccessTransactionService);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     CampusAccessResource.class,
                                     AccessingCampusExceptionAssembler.class,
                                     CarExceptionAssembler.class,
                                     CampusAccessExceptionAssembler.class);
  }
}
