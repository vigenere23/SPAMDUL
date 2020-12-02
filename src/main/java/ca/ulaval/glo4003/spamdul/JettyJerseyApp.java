package ca.ulaval.glo4003.spamdul;

import ca.ulaval.glo4003.spamdul.context.main.ContextType;
import ca.ulaval.glo4003.spamdul.context.main.MainContext;
import ca.ulaval.glo4003.spamdul.context.main.MainContextFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.http.CORSResponseFilter;
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

public class JettyJerseyApp implements SpamdUlApplication {

  private final MainContext mainContext;
  private final Server server;

  public JettyJerseyApp() {
    ContextType contextType = ContextType.parse(System.getenv("SPAMDUL_API_MODE"));
    mainContext = new MainContextFactory().create(contextType);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        Set<Object> resources = new HashSet<>();
        mainContext.registerResources(resources);
        return resources;
      }
    });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    contextHandler.addServlet(servletHolder, "/*");

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{contextHandler});
    server = new Server(8080);
    server.setHandler(contexts);
  }

  @Override public void start() {
    try {
      server.start();
      server.join();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      server.destroy();
      mainContext.destroy();
    }
  }
}
