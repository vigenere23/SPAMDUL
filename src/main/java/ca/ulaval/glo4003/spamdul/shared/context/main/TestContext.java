package ca.ulaval.glo4003.spamdul.shared.context.main;

import ca.ulaval.glo4003.spamdul.authentication.context.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.charging.context.DevChargingContext;
import ca.ulaval.glo4003.spamdul.finance.context.carboncredits.DevCarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.finance.context.initiatives.ProdInitiativesContext;
import ca.ulaval.glo4003.spamdul.finance.context.revenue.RevenueContext;
import ca.ulaval.glo4003.spamdul.parking.context.bikeparkingaccess.BikeParkingAccessContext;
import ca.ulaval.glo4003.spamdul.parking.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.parking.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.parking.context.parkinguser.ParkingUserContext;
import ca.ulaval.glo4003.spamdul.parking.context.pass.DevPassContext;
import ca.ulaval.glo4003.spamdul.shared.api.PingResource;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import ca.ulaval.glo4003.spamdul.usage.context.ProdUsageContext;

public class TestContext extends MainContext {

  public TestContext() {
    authContext = new AuthenticationContext();
    parkingUserContext = new ParkingUserContext();
    usageContext = new ProdUsageContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    revenueContext = new RevenueContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    campusAccessContext = new CampusAccessContext(parkingUserContext.getUserRepository(),
                                                  usageContext.getParkingAccessLogger(),
                                                  revenueContext.getCampusAccessBankAccount());
    bikeParkingAccessContext = new BikeParkingAccessContext(parkingUserContext.getUserRepository(),
                                                            usageContext.getParkingAccessLogger());
    passContext = new DevPassContext(revenueContext.getPassBankAccount(),
                                     parkingUserContext.getUserRepository());
    chargingContext = new DevChargingContext(revenueContext.getTransactionFactory(),
                                             parkingUserContext.getUserRepository(),
                                             parkingUserContext.getCarFactory());
    initiativesContext = new ProdInitiativesContext(revenueContext.getInitiativesBankAccount(),
                                                    authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    infractionsContext = new InfractionsContext(authContext.getAuthenticationRepository(),
                                                parkingUserContext.getUserRepository(),
                                                authContext.getAccessTokenCookieAssembler(),
                                                revenueContext.getInfractionBankAccount());
    carbonCreditsContext = new DevCarbonCreditsContext(revenueContext.getCarbonCreditsBankAccount(),
                                                       revenueContext.getSustainabilityBankAccount(),
                                                       initiativesContext.getInitiativeRepository(),
                                                       initiativesContext.getInitiativeCreator(),
                                                       authContext.getAuthenticationRepository(),
                                                       authContext.getAccessTokenCookieAssembler());
  }

  @Override
  public void registerResources(InstanceMap resources) {
    super.registerResources(resources);
    resources.add(new PingResource());
  }
}
