package ca.ulaval.glo4003.spamdul.context.main;

import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.DevCarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.DevChargingContext;
import ca.ulaval.glo4003.spamdul.context.finance.FinanceContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.DevFundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.DevPassContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.DevUsageReportContext;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.PingResource;
import ca.ulaval.glo4003.spamdul.utils.InstanceMap;

public class DevContext extends MainContext {

  public DevContext() {
    authContext = new AuthenticationContext();
    usageReportContext = new DevUsageReportContext(authContext.getAuthenticationRepository(),
                                                   authContext.getAccessTokenCookieAssembler());
    financeContext = new FinanceContext(authContext.getAuthenticationRepository(),
                                        authContext.getAccessTokenCookieAssembler());
    campusAccessContext = new CampusAccessContext(usageReportContext.getParkingAccessLogger(),
                                                  financeContext.getCampusAccessBankAccount()
    );
    passContext = new DevPassContext(financeContext.getPassBankAccount(),
                                     campusAccessContext.getCampusAccessService());
    chargingContext = new DevChargingContext(financeContext.getTransactionFactory());
    fundraisingContext = new DevFundraisingContext(financeContext.getInitiativesBankAccount(),
                                                   authContext.getAuthenticationRepository(),
                                                   authContext.getAccessTokenCookieAssembler());
    infractionsContext = new InfractionsContext(passContext.getPassRepository(),
                                                authContext.getAuthenticationRepository(),
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
