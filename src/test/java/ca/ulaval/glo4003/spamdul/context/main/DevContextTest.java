package ca.ulaval.glo4003.spamdul.context.main;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.SpamDULExceptionAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.ui.PingResource;
import ca.ulaval.glo4003.spamdul.ui.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.ui.bikeparkingaccess.BikeParkingAccessResource;
import ca.ulaval.glo4003.spamdul.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.ui.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.ui.charging.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.ui.finance.RevenueResource;
import ca.ulaval.glo4003.spamdul.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.ui.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.ui.user.UserResource;
import org.junit.Before;
import org.junit.Test;

public class DevContextTest {

  private DevContext context;
  private InstanceMap resources;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new DevContext();
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     GlobalExceptionAssembler.class,
                                     PingResource.class,
                                     AuthenticationResource.class,
                                     CampusAccessResource.class,
                                     CarbonCreditsResource.class,
                                     CarbonCreditsResourceAdmin.class,
                                     ChargingPointResource.class,
                                     RechargULResource.class,
                                     RevenueResource.class,
                                     FundraisingResource.class,
                                     InfractionResource.class,
                                     ParkingPassResource.class,
                                     UsageReportResource.class,
                                     UserResource.class,
                                     BikeParkingAccessResource.class,
                                     SpamDULExceptionAssembler.class);
  }
}
