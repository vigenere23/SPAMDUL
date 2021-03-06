package ca.ulaval.glo4003.spamdul.shared.application;

import ca.ulaval.glo4003.spamdul.shared.context.main.ContextFactory;
import ca.ulaval.glo4003.spamdul.shared.context.main.ContextType;
import ca.ulaval.glo4003.spamdul.shared.context.main.MainContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import java.net.InetSocketAddress;
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

  private final MainContext context;
  private final Server server;

  public JettyJerseyApp(ContextFactory contextFactory) {
    ContextType contextType = ContextType.parse(System.getenv("SPAMDUL_API_MODE"));
    context = contextFactory.create(contextType);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath('/' + context.getApiUrl().getPrefix());
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        InstanceMap instanceMap = new InstanceMap();
        context.registerResources(instanceMap);
        return instanceMap.getValues();
      }
    });

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    contextHandler.addServlet(servletHolder, "/*");

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[]{contextHandler});
    server = new Server(new InetSocketAddress(context.getApiUrl().getHostName(), context.getApiUrl().getPortNumber()));
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
      context.destroy();
    }
  }
}
