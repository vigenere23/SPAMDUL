package ca.ulaval.glo4003.spamdul.parking.entities.infractions;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.shared.entities.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfractionIdFactoryTest {

  private static final String A_VALUE = "123";

  @Mock
  private IdGenerator idGenerator;

  private InfractionIdFactory infractionIdFactory;

  @Before
  public void setUp() {
    infractionIdFactory = new InfractionIdFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generate()).thenReturn(A_VALUE);

    InfractionId infractionId = infractionIdFactory.create();

    assertThat(infractionId.toString()).isEqualTo(A_VALUE);
  }
}
