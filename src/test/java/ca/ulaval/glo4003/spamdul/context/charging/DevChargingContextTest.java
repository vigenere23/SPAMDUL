package ca.ulaval.glo4003.spamdul.context.charging;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.charging.api.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.charging.api.chargingpoint.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.finance.entities.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserRepository;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.car.CarFactory;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DevChargingContextTest {

  private DevChargingContext context;
  private InstanceMap resources;

  @Mock
  private TransactionFactory transactionFactory;
  @Mock
  private UserRepository userRepository;
  @Mock
  private CarFactory carFactory;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevChargingContext(transactionFactory, userRepository, carFactory);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     ChargingPointResource.class,
                                     RechargULResource.class);
  }
}
