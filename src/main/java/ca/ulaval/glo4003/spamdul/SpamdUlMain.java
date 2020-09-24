package ca.ulaval.glo4003.spamdul;

import ca.ulaval.glo4003.spamdul.entity.contact.Contact;
import ca.ulaval.glo4003.spamdul.entity.contact.ContactAssembler;
import ca.ulaval.glo4003.spamdul.entity.contact.ContactRepository;
import ca.ulaval.glo4003.spamdul.entity.contact.ContactService;
import ca.ulaval.glo4003.spamdul.entity.user.UserFactory;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.infrastructure.db.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.contact.ContactRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.db.user.UserRepositoryInMemory;
import ca.ulaval.glo4003.spamdul.infrastructure.http.CORSResponseFilter;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.contact.ContactResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.contact.ContactResourceImpl;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.UserResource;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.UserResourceImpl;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.UserExceptionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.user.UserService;
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

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class SpamdUlMain {

  public static boolean isDev = true; // Would be a JVM argument or in a .property file

  public static void main(String[] args)
      throws Exception {

    // Setup resources (API)
    //    ContactResource contactResource = createContactResource();
    UserResource userResource = createUserResource();

    // Setup API context (JERSEY + JETTY)
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/api/");
    ResourceConfig resourceConfig = ResourceConfig.forApplication(new Application() {
      @Override
      public Set<Object> getSingletons() {
        //TODO Bouger ce qu'il y a ici dans un ServiceLocator
        HashSet<Object> resources = new HashSet<>();
        // Add resources to context
        //        resources.add(contactResource);
        resources.add(userResource);
        resources.add(new UserExceptionAssembler());
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
    } finally {
      server.destroy();
    }
  }

  private static UserResource createUserResource() {
    UserRepository userRepository = new UserRepositoryInMemory();
    UserAssembler userAssembler = new UserAssembler();
    UserFactory userFactory = new UserFactory();
    UserService userService = new UserService(userFactory, userRepository);
    UserResource userResource = new UserResourceImpl(userService, userAssembler);

    return userResource;
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