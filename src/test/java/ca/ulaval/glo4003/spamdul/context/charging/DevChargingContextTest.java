package ca.ulaval.glo4003.spamdul.context.charging;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.entity.finance.transaction.TransactionFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULExceptionMapper;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DevChargingContextTest {

  private DevChargingContext context;
  private InstanceMap resources;

  @Mock
  private TransactionFactory transactionFactory;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevChargingContext(transactionFactory);
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
