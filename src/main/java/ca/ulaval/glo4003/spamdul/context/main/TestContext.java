package ca.ulaval.glo4003.spamdul.context.main;

import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.bikeparkingaccess.BikeParkingAccessContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.DevCarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.DevChargingContext;
import ca.ulaval.glo4003.spamdul.context.finance.FinanceContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.ProdFundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.DevPassContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.ProdUsageReportContext;
import ca.ulaval.glo4003.spamdul.context.user.UserContext;
import ca.ulaval.glo4003.spamdul.ui.PingResource;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class TestContext extends MainContext {

  public TestContext() {
    authContext = new AuthenticationContext();
    userContext = new UserContext();
    bikeParkingAccessContext = new BikeParkingAccessContext(userContext.getUserRepository());
    usageReportContext = new ProdUsageReportContext(authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    financeContext = new FinanceContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    campusAccessContext = new CampusAccessContext(userContext.getUserRepository(),
                                                  usageReportContext.getParkingAccessLogger(),
                                                  financeContext.getCampusAccessBankAccount()
    );
    passContext = new DevPassContext(financeContext.getPassBankAccount(),
                                     userContext.getUserRepository());
    chargingContext = new DevChargingContext(financeContext.getTransactionFactory(),
                                             userContext.getUserRepository());
    fundraisingContext = new ProdFundraisingContext(financeContext.getInitiativesBankAccount(),
                                                    authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    infractionsContext = new InfractionsContext(authContext.getAuthenticationRepository(),
                                                userContext.getUserRepository(),
                                                authContext.getAccessTokenCookieAssembler(),
                                                financeContext.getInfractionBankAccount());
    carbonCreditsContext = new DevCarbonCreditsContext(financeContext.getCarbonCreditsBankAccount(),
                                                       financeContext.getSustainabilityBankAccount(),
                                                       fundraisingContext.getInitiativeRepository(),
                                                       fundraisingContext.getInitiativeCreator(),
                                                       authContext.getAuthenticationRepository(),
                                                       authContext.getAccessTokenCookieAssembler());
  }

  @Override
  public void registerResources(InstanceMap resources) {
    super.registerResources(resources);
    resources.add(new PingResource());
  }
}
