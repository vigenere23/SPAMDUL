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
import org.mockito.Mock;

public class ProdChargingContextTest {

  private ProdChargingContext context;
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
    context = new ProdChargingContext(transactionFactory, userRepository, carFactory);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     ChargingPointResource.class,
                                     RechargULResource.class);
  }
}
