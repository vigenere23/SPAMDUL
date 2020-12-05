package ca.ulaval.glo4003.spamdul.context.main;

import static ca.ulaval.glo4003.spamdul.utils.Matchers.assertContainsExactlyInstancesOf;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.PingResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AuthenticationResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.CampusAccessResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.CarbonCreditsResourceAdmin;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.charging_point.ChargingPointResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.finance.RevenueResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.FundraisingResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.InfractionResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.PassResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.RechargULResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.UsageReportResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.UserResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.authentication.AuthenticationExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.ChargingPointExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.finance.FinanceExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.CampusAccessExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.PassExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;
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
                                     TimePeriodExceptionAssembler.class,
                                     PingResource.class,
                                     AuthenticationResource.class,
                                     AuthenticationExceptionAssembler.class,
                                     UserExceptionAssembler.class,
                                     CampusAccessResource.class,
                                     AccessingCampusExceptionAssembler.class,
                                     CarExceptionAssembler.class,
                                     CampusAccessExceptionAssembler.class,
                                     CarbonCreditsResource.class,
                                     CarbonCreditsResourceAdmin.class,
                                     ChargingPointResource.class,
                                     ChargingPointExceptionMapper.class,
                                     RechargULResource.class,
                                     RechargULExceptionMapper.class,
                                     RevenueResource.class,
                                     FinanceExceptionMapper.class,
                                     FundraisingResource.class,
                                     InitiativeExceptionMapper.class,
                                     InfractionResource.class,
                                     InfractionExceptionAssembler.class,
                                     PassResource.class,
                                     PassExceptionAssembler.class,
                                     DeliveryExceptionAssembler.class,
                                     UsageReportResource.class,
                                     UsageReportExceptionAssembler.class,
                                     UserResource.class);
  }
}
