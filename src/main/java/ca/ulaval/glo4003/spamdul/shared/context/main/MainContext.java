package ca.ulaval.glo4003.spamdul.shared.context.main;

import ca.ulaval.glo4003.spamdul.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.SpamDULExceptionAssembler;
import ca.ulaval.glo4003.spamdul.authentication.context.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.charging.context.ChargingContext;
import ca.ulaval.glo4003.spamdul.finance.context.carboncredits.CarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.finance.context.initiatives.InitiativesContext;
import ca.ulaval.glo4003.spamdul.finance.context.revenue.RevenueContext;
import ca.ulaval.glo4003.spamdul.invoice.context.InvoiceContext;
import ca.ulaval.glo4003.spamdul.parking.context.bikeparkingaccess.BikeParkingAccessContext;
import ca.ulaval.glo4003.spamdul.parking.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.parking.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.parking.context.parkinguser.ParkingUserContext;
import ca.ulaval.glo4003.spamdul.parking.context.pass.PassContext;
import ca.ulaval.glo4003.spamdul.parking2.context.ParkingContext;
import ca.ulaval.glo4003.spamdul.shared.api.ApiUrl;
import ca.ulaval.glo4003.spamdul.shared.context.ResourceContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usage.context.UsageContext;

public abstract class MainContext implements ResourceContext {

  protected AuthenticationContext authContext;
  protected ParkingUserContext parkingUserContext;
  protected UsageContext usageContext;
  protected RevenueContext revenueContext;
  protected CampusAccessContext campusAccessContext;
  protected PassContext passContext;
  protected ChargingContext chargingContext;
  protected InitiativesContext initiativesContext;
  protected InfractionsContext infractionsContext;
  protected CarbonCreditsContext carbonCreditsContext;
  protected BikeParkingAccessContext bikeParkingAccessContext;
  protected InvoiceContext invoiceContext;
  protected ParkingContext parkingContext;

  @Override
  public void registerResources(InstanceMap resources) {
    resources.add(new GlobalExceptionAssembler());
    resources.add(new SpamDULExceptionAssembler());

    authContext.registerResources(resources);
    parkingUserContext.registerResources(resources);
    usageContext.registerResources(resources);
    revenueContext.registerResources(resources);
    campusAccessContext.registerResources(resources);
    passContext.registerResources(resources);
    chargingContext.registerResources(resources);
    initiativesContext.registerResources(resources);
    infractionsContext.registerResources(resources);
    carbonCreditsContext.registerResources(resources);
    bikeParkingAccessContext.registerResources(resources);
    invoiceContext.registerResources(resources);
    parkingContext.registerResources(resources);
  }

  public void destroy() {
    carbonCreditsContext.getEndOfMonthEventScheduler().stopJob();
  }

  public abstract ApiUrl getApiUrl();
}
