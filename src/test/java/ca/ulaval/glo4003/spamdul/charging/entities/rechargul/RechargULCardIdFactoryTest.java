package ca.ulaval.glo4003.spamdul.charging.entities.rechargul;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RechargULCardIdFactoryTest {

  private static final String A_VALUE = "123";

  @Mock
  private IdGenerator idGenerator;

  private RechargULCardIdFactory rechargULCardIdFactory;

  @Before
  public void setUp() {
    rechargULCardIdFactory = new RechargULCardIdFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generate()).thenReturn(A_VALUE);

    RechargULCardId rechargULCardId = rechargULCardIdFactory.create();

    assertThat(rechargULCardId.toString()).isEqualTo(A_VALUE);
  }
}
