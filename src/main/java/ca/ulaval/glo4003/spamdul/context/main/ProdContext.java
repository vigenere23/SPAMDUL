package ca.ulaval.glo4003.spamdul.context.main;

import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.ProdCarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.ProdChargingContext;
import ca.ulaval.glo4003.spamdul.context.finance.FinanceContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.ProdFundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.ProdPassContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.ProdUsageReportContext;

public class ProdContext extends MainContext {

  public ProdContext() {
    authContext = new AuthenticationContext();
    usageReportContext = new ProdUsageReportContext(authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    financeContext = new FinanceContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    campusAccessContext = new CampusAccessContext(usageReportContext.getParkingAccessLogger(),
                                                  financeContext.getCampusAccessBankAccount()
    );
    passContext = new ProdPassContext(financeContext.getPassBankAccount(),
                                      campusAccessContext.getCampusAccessService());
    chargingContext = new ProdChargingContext(financeContext.getTransactionFactory());
    fundraisingContext = new ProdFundraisingContext(financeContext.getInitiativesBankAccount(),
                                                    authContext.getAuthenticationRepository(),
                                                    authContext.getAccessTokenCookieAssembler());
    infractionsContext = new InfractionsContext(passContext.getPassRepository(),
                                                authContext.getAuthenticationRepository(),
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
