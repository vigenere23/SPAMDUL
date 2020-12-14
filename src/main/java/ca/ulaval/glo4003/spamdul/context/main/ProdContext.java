package ca.ulaval.glo4003.spamdul.context.main;

import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.bikeparkingaccess.BikeParkingAccessContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.ProdCarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.ProdChargingContext;
import ca.ulaval.glo4003.spamdul.context.finance.FinanceContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.ProdFundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.ProdPassContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.ProdUsageReportContext;
import ca.ulaval.glo4003.spamdul.context.user.UserContext;

public class ProdContext extends MainContext {

  public ProdContext() {
    authContext = new AuthenticationContext();
    userContext = new UserContext();
    usageReportContext = new ProdUsageReportContext(authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    financeContext = new FinanceContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    campusAccessContext = new CampusAccessContext(userContext.getUserRepository(),
                                                  usageReportContext.getParkingAccessLogger(),
                                                  financeContext.getCampusAccessBankAccount());
    bikeParkingAccessContext = new BikeParkingAccessContext(userContext.getUserRepository(),
                                                            usageReportContext.getParkingAccessLogger());
    passContext = new ProdPassContext(financeContext.getPassBankAccount(),
                                      userContext.getUserRepository());
    chargingContext = new ProdChargingContext(financeContext.getTransactionFactory(),
                                              userContext.getUserRepository(),
                                              userContext.getCarFactory());
    fundraisingContext = new ProdFundraisingContext(financeContext.getInitiativesBankAccount(),
                                                    authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    infractionsContext = new InfractionsContext(authContext.getAuthenticationRepository(),
                                                userContext.getUserRepository(),
                                                authContext.getAccessTokenCookieAssembler(),
                                                financeContext.getInfractionBankAccount());
    carbonCreditsContext = new ProdCarbonCreditsContext(financeContext.getCarbonCreditsBankAccount(),
                                                        financeContext.getSustainabilityBankAccount(),
                                                        fundraisingContext.getInitiativeRepository(),
                                                        fundraisingContext.getInitiativeCreator(),
                                                        authContext.getAuthenticationRepository(),
                                                        authContext.getAccessTokenCookieAssembler());
  }
}
