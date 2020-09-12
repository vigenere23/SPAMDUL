package ca.ulaval.glo4003.projet.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import ca.ulaval.glo4003.projet.base.ws.api.contact.ContactResource;
import ca.ulaval.glo4003.projet.base.ws.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.Contact;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactRepository;
import ca.ulaval.glo4003.projet.base.ws.domain.contact.ContactService;
import ca.ulaval.glo4003.projet.base.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.projet.base.ws.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.projet.base.ws.infrastructure.contact.ContactRepositoryInMemory;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class ProjetBaseMain {
  public static boolean isDev = true; // Would be a JVM argument or in a .property file

  public static void main(String[] args)
          throws Exception {

    // Setup resources (API)
    ContactResource contactResource = createContactResource();

    // Setup API context (JERSEY + JETTY)
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        HashSet<Object> resources = new HashSet<>();
        // Add resources to context
        resources.add(contactResource);
        return resources;
      }
    });
    resourceConfig.register(CORSResponseFilter.class);

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);
    context.addServlet(servletHolder, "/*");

    // Setup http server
    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] { context });
    Server server = new Server(8080);
    server.setHandler(contexts);

    try {
      server.start();
      server.join();
    } finally {
      server.destroy();
    }
  }

  private static ContactResource createContactResource() {
    // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
    ContactRepository contactRepository = new ContactRepositoryInMemory();

    // For development ease
    if (isDev) {
      ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
      List<Contact> contacts = contactDevDataFactory.createMockData();
      contacts.stream().forEach(contactRepository::save);
    }

    ContactAssembler contactAssembler = new ContactAssembler();
    ContactService contactService = new ContactService(contactRepository, contactAssembler);

    return new ContactResourceImpl(contactService);
  }

}
