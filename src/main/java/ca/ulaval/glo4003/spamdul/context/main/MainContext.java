package ca.ulaval.glo4003.spamdul.context.main;

import ca.ulaval.glo4003.spamdul.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.bikeparkingaccess.BikeParkingAccessContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.CarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.ChargingContext;
import ca.ulaval.glo4003.spamdul.context.finance.FinanceContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.FundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.PassContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.UsageReportContext;
import ca.ulaval.glo4003.spamdul.context.user.UserContext;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.SpamDULExceptionAssembler;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;

public abstract class MainContext implements ResourceContext {

  protected AuthenticationContext authContext;
  protected UserContext userContext;
  protected UsageReportContext usageReportContext;
  protected FinanceContext financeContext;
  protected CampusAccessContext campusAccessContext;
  protected PassContext passContext;
  protected ChargingContext chargingContext;
  protected FundraisingContext fundraisingContext;
  protected InfractionsContext infractionsContext;
  protected CarbonCreditsContext carbonCreditsContext;
  protected BikeParkingAccessContext bikeParkingAccessContext;

  @Override public void registerResources(InstanceMap resources) {
    resources.add(new GlobalExceptionAssembler());
    resources.add(new SpamDULExceptionAssembler());

    authContext.registerResources(resources);
    userContext.registerResources(resources);
    usageReportContext.registerResources(resources);
    financeContext.registerResources(resources);
    campusAccessContext.registerResources(resources);
    passContext.registerResources(resources);
    chargingContext.registerResources(resources);
    fundraisingContext.registerResources(resources);
    infractionsContext.registerResources(resources);
    carbonCreditsContext.registerResources(resources);
    bikeParkingAccessContext.registerResources(resources);
  }

  public void destroy() {
    carbonCreditsContext.getEndOfMonthEventScheduler().stopJob();
  }
}
