package ca.ulaval.glo4003.spamdul.context.main;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.SpamDULExceptionAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.api.PingResource;
import ca.ulaval.glo4003.spamdul.api.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.api.bikeparkingaccess.BikeParkingAccessResource;
import ca.ulaval.glo4003.spamdul.api.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.api.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.api.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.api.charging.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.api.finance.RevenueResource;
import ca.ulaval.glo4003.spamdul.api.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.api.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.api.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.api.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.api.user.UserResource;
import org.junit.Before;
import org.junit.Test;

public class TestContextTest {

  private TestContext context;
  private InstanceMap resources;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new TestContext();
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
