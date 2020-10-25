package ca.ulaval.glo4003.spamdul;

import ca.ulaval.glo4003.spamdul.context.account.AccountContext;
import ca.ulaval.glo4003.spamdul.context.campusaccess.CampusAccessContext;
import ca.ulaval.glo4003.spamdul.context.carboncredits.CarbonCreditsContext;
import ca.ulaval.glo4003.spamdul.context.fundraising.FundraisingContext;
import ca.ulaval.glo4003.spamdul.context.infractions.InfractionsContext;
import ca.ulaval.glo4003.spamdul.context.revenue.RevenueContext;
import ca.ulaval.glo4003.spamdul.context.sale.SaleContext;
import ca.ulaval.glo4003.spamdul.context.usagereport.UsageReportContext;
import ca.ulaval.glo4003.spamdul.infrastructure.http.CORSResponseFilter;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.GlobalExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.AccessingCampusExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.CampusAccessExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.delivery.DeliveryExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeExceptionMapper;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionExceptionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleExceptionAssembler;
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

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class SpamdUlMain {

  public static boolean isDev = true; // Would be a JVM argument or in a .property file

  public static void main(String[] args)
      throws Exception {

    UsageReportContext usageReportContext = new UsageReportContext(false);
    AccountContext accountContext = new AccountContext();
    SaleContext saleContext = new SaleContext(accountContext.bankRepository());
    CampusAccessContext campusAccessContext = new CampusAccessContext(saleContext.getPassRepository(),
                                                                      usageReportContext.getParkingAccessLogger(),
                                                                      accountContext.bankRepository()
                                                                      );
    saleContext.setCampusAccessService(campusAccessContext.getCampusAccessService());
    CarbonCreditsContext carbonCreditsContext = new CarbonCreditsContext(accountContext.bankRepository());
    FundraisingContext fundraisingContext = new FundraisingContext(accountContext.bankRepository(),
                                                                   false);
    RevenueContext revenueContext = new RevenueContext(accountContext.bankRepository(), false);
    InfractionsContext infractionsContext = new InfractionsContext(saleContext.getPassRepository(),
                                                                   accountContext.bankRepository());

    // Setup API context (JERSEY + JETTY)
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        //TODO Bouger ce qu'il y a ici dans un ServiceLocator
        HashSet<Object> resources = new HashSet<>();

        resources.add(saleContext.getSaleResource());
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
        resources.add(new PassSaleExceptionAssembler());
        resources.add(new DeliveryExceptionAssembler());
        resources.add(carbonCreditsContext.getCarbonCreditsResource());
        resources.add(fundraisingContext.getFundraisingResource());
        resources.add(new InitiativeExceptionMapper());
        resources.add(infractionsContext.getInfractionResource());
        resources.add(new InfractionExceptionAssembler());

        return resources;
      }
    });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    // Setup http server
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
