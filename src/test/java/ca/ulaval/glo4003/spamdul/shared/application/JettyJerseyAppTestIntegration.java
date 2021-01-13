package ca.ulaval.glo4003.spamdul.shared.application;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.shared.api.ApiUrl;
import ca.ulaval.glo4003.spamdul.shared.context.main.ContextFactory;
import ca.ulaval.glo4003.spamdul.shared.context.main.ContextType;
import ca.ulaval.glo4003.spamdul.shared.context.main.MainContext;
import ca.ulaval.glo4003.spamdul.shared.utils.InstanceMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JettyJerseyAppTestIntegration {

  private static final String MODE_VAR_NAME = "SPAMDUL_API_MODE";
  private static final int SERVER_UP_TIME_IN_MILLIS = 400;
  private static final ApiUrl API_URL = new ApiUrl("localhost", 8080, "");

  @Mock
  private ContextFactory contextFactory;
  @Mock
  private MainContext context;

  @Before
  public void setUp() {
    when(context.getApiUrl()).thenReturn(API_URL);
  }

  @Test
  public void whenCreating_shouldNotThrowException() {
    when(contextFactory.create(any(ContextType.class))).thenReturn(context);
    new JettyJerseyApp(contextFactory);
  }

  @Test
  public void givenTestMode_whenCreating_shouldCreateWithFactory() throws Exception {
    when(contextFactory.create(any(ContextType.class))).thenReturn(context);
    withEnvironmentVariable(MODE_VAR_NAME, "test").execute(() -> {
      new JettyJerseyApp(contextFactory);
    });

    verify(contextFactory).create(ContextType.TEST);
  }

  @Test
  public void givenTestMode_whenStarting_shouldRegisterRoutes() throws Exception {
    when(contextFactory.create(ContextType.TEST)).thenReturn(context);
    SpamdUlApplication app = withEnvironmentVariable(MODE_VAR_NAME, "test").execute(() -> new JettyJerseyApp(
        contextFactory));

    startApp(app);

    verify(context, times(2)).registerResources(any(InstanceMap.class));
  }

  @Test
  public void givenDevMode_whenCreating_shouldCreateWithFactory() throws Exception {
    when(contextFactory.create(any(ContextType.class))).thenReturn(context);
    withEnvironmentVariable(MODE_VAR_NAME, "dev").execute(() -> {
      new JettyJerseyApp(contextFactory);
    });

    verify(contextFactory).create(ContextType.DEV);
  }

  @Test
  public void givenDevMode_whenStarting_shouldRegisterRoutes() throws Exception {
    when(contextFactory.create(ContextType.DEV)).thenReturn(context);
    SpamdUlApplication app = withEnvironmentVariable(MODE_VAR_NAME, "dev").execute(() -> new JettyJerseyApp(
        contextFactory));

    startApp(app);

    verify(context, times(2)).registerResources(any(InstanceMap.class));
  }

  @Test
  public void givenProdMode_whenCreating_shouldCreateWithFactory() throws Exception {
    when(contextFactory.create(any(ContextType.class))).thenReturn(context);
    withEnvironmentVariable(MODE_VAR_NAME, "prod").execute(() -> {
      new JettyJerseyApp(contextFactory);
    });

    verify(contextFactory).create(ContextType.PROD);
  }

  @Test
  public void givenProdMode_whenStarting_shouldRegisterRoutes() throws Exception {
    when(contextFactory.create(ContextType.PROD)).thenReturn(context);
    SpamdUlApplication app = withEnvironmentVariable(MODE_VAR_NAME, "prod").execute(() -> new JettyJerseyApp(
        contextFactory));

    startApp(app);

    verify(context, times(2)).registerResources(any(InstanceMap.class));
  }

  private void startApp(SpamdUlApplication app) {
    try {
      Thread appThread = new Thread(app::start);
      appThread.start();
      appThread.join(SERVER_UP_TIME_IN_MILLIS);
      appThread.interrupt();
    } catch (InterruptedException e) {
      // Normal, tout est chill
    }
  }
}
