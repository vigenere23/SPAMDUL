package ca.ulaval.glo4003.spamdul;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.context.main.ContextFactory;
import ca.ulaval.glo4003.spamdul.context.main.ContextType;
import ca.ulaval.glo4003.spamdul.context.main.MainContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JettyJerseyAppTest {

  private static final String MODE_VAR_NAME = "SPAMDUL_API_MODE";
  private static final int SERVER_UP_TIME_IN_MILLIS = 300;

  @Mock
  private ContextFactory contextFactory;
  @Mock
  private MainContext context;

  @Test
  public void whenCreating_shouldNotThrowException() {
    new JettyJerseyApp(contextFactory);
  }

  @Test
  public void givenTestMode_whenCreating_shouldCreateWithFactory() throws Exception {
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

    verify(context, times(2)).registerResources(anySet());
  }

  @Test
  public void givenDevMode_whenCreating_shouldCreateWithFactory() throws Exception {
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

    verify(context, times(2)).registerResources(anySet());
  }

  @Test
  public void givenProdMode_whenCreating_shouldCreateWithFactory() throws Exception {
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

    verify(context, times(2)).registerResources(anySet());
  }

  private void startApp(SpamdUlApplication app) throws InterruptedException {
    Thread appThread = new Thread(app::start);
    appThread.start();
    appThread.join(SERVER_UP_TIME_IN_MILLIS);
  }
}
