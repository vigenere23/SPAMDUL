package ca.ulaval.glo4003.spamdul.shared.context.main;

import static ca.ulaval.glo4003.spamdul.shared.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.SpamDULExceptionAssembler;
import ca.ulaval.glo4003.spamdul.authentication.api.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.charging.api.chargingpoint.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.charging.api.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.finance.api.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.FundraisingResource;
import ca.ulaval.glo4003.spamdul.finance.api.revenue.RevenueResource;
import ca.ulaval.glo4003.spamdul.parking.api.bikeparkingaccess.BikeParkingAccessResource;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.parking.api.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.parking.api.parkinguser.UserResource;
import ca.ulaval.glo4003.spamdul.parking.api.pass.ParkingPassResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usage.api.usagereport.UsageReportResource;
import org.junit.Before;
import org.junit.Test;

public class ProdContextTest {

  private ProdContext context;
  private InstanceMap resources;

  @Before
  public void setUp() {
    resources = new InstanceMap();
    context = new ProdContext();
  }

  @Test
  public void whenRegisteringResources_shouldAddResourcesToInstanceMap() {
    context.registerResources(resources);

    assertContainsExactlyInstancesOf(resources.getValues(),
                                     GlobalExceptionAssembler.class,
                                     AuthenticationResource.class,
                                     CampusAccessResource.class,
                                     CarbonCreditsResource.class,
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
