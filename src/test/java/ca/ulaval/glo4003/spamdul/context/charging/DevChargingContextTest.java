package ca.ulaval.glo4003.spamdul.context.charging;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.ui.charging.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULExceptionMapper;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
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

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevChargingContext(transactionFactory, userRepository);
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);
    assertContainsExactlyInstancesOf(resources.getValues(),
                                     ChargingPointResource.class,
                                     ChargingPointExceptionMapper.class,
                                     RechargULResource.class,
                                     RechargULExceptionMapper.class);
  }
}
