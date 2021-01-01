package ca.ulaval.glo4003.spamdul.shared.context.main;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ContextFactoryTest {

  private ContextFactory contextFactory;

  @Before
  public void setUp() {
    contextFactory = new ContextFactory();
  }

  @Test
  public void givenTestMode_whenCreating_shouldReturnTestContext() {
    MainContext context = contextFactory.create(ContextType.TEST);

    assertThat(context).isInstanceOf(TestContext.class);
  }

  @Test
  public void givenDevMode_whenCreating_shouldReturnTestContext() {
    MainContext context = contextFactory.create(ContextType.DEV);

    assertThat(context).isInstanceOf(DevContext.class);
  }

  @Test
  public void givenProdMode_whenCreating_shouldReturnTestContext() {
    MainContext context = contextFactory.create(ContextType.PROD);

    assertThat(context).isInstanceOf(ProdContext.class);
  }
}
