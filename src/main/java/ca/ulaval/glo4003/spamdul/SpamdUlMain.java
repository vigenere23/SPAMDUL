package ca.ulaval.glo4003.spamdul;

import ca.ulaval.glo4003.spamdul.context.GlobalContext;
import ca.ulaval.glo4003.spamdul.context.account.AccountContext;
import ca.ulaval.glo4003.spamdul.context.authentication.AuthenticationContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.CarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.charging.ChargingContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.FundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.pass.PassContext;
import ca.ulaval.glo4003.spamdul.context.revenue.RevenueContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.UsageReportContext;
import ca.ulaval.glo4003.spamdul.infrastructure.http.CORSResponseFilter;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.PingResource;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.pass.PassExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.timeperiod.TimePeriodExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportExceptionAssembler;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

@SuppressWarnings("all")
public class SpamdUlMain {

  public static void main(String[] args)
      throws Exception {

    GlobalContext globalContext = new GlobalContext();
    AuthenticationContext authenticationContext = new AuthenticationContext();
    UsageReportContext usageReportContext = new UsageReportContext(false);
    AccountContext accountContext = new AccountContext();
    CampusAccessContext campusAccessContext = new CampusAccessContext(globalContext.getPassRepository(),
                                                                      usageReportContext.getParkingAccessLogger(),
                                                                      accountContext.bankRepository(),
                                                                      globalContext.getTransactionFactory()
    );
    PassContext passContext = new PassContext(accountContext.bankRepository(),
                                              globalContext.getPassRepository(),
                                              campusAccessContext.getCampusAccessService());
    FundraisingContext fundraisingContext = new FundraisingContext(accountContext.bankRepository(),
                                                                   false);
    CarbonCreditsContext carbonCreditsContext = new CarbonCreditsContext(accountContext.bankRepository(),
                                                                         globalContext.getTransactionFactory(),
                                                                         fundraisingContext.getInitiativeFactory(),
                                                                         fundraisingContext.getInitiativeRepository(),
                                                                         true);
    RevenueContext revenueContext = new RevenueContext(accountContext.bankRepository(), false);
    InfractionsContext infractionsContext = new InfractionsContext(globalContext.getPassRepository(),
                                                                   accountContext.bankRepository(),
                                                                   authenticationContext.getAuthenticationRepository());
                                                                   accountContext.bankRepository());
    ChargingContext chargingContext = new ChargingContext();

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();

        resources.add(new PingResource());
        resources.add(passContext.getPassResource());
        resources.add(campusAccessContext.getCampusAccessResource());
        resources.add(usageReportContext.getUsageReportResource());
        resources.add(revenueContext.getRevenueResource());
        resources.add(new UsageReportExceptionAssembler());
        resources.add(new UserExceptionAssembler());
        resources.add(new CarExceptionAssembler());
        resources.add(new CampusAccessExceptionAssembler());
        resources.add(new AccessingCampusExceptionAssembler());
        resources.add(new TimePeriodExceptionAssembler());
        resources.add(new GlobalExceptionAssembler());
        resources.add(new PassExceptionAssembler());
        resources.add(new DeliveryExceptionAssembler());
        resources.add(carbonCreditsContext.getCarbonCreditsResource());
        resources.add(carbonCreditsContext.getCarbonCreditsResourceAdmin());
        resources.add(fundraisingContext.getFundraisingResource());
        resources.add(new InitiativeExceptionMapper());
        resources.add(infractionsContext.getInfractionResource());
        resources.add(new InfractionExceptionAssembler());
        resources.add(chargingContext.getChargingPointResource());
        resources.add(authenticationContext.getAuthenticationResource());

        return resources;
      }
    });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{context});
    Server server = new Server(8080);
    server.setHandler(contexts);

    try {
      server.start();
      server.join();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      server.destroy();
      carbonCreditsContext.getEndOfMonthEventScheduler().stopJob();
    }
  }
}
