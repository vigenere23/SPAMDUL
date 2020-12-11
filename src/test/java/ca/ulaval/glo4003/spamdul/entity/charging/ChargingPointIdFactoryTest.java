package ca.ulaval.glo4003.spamdul.entity.charging;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointIdFactoryTest {

  private static final String A_VALUE = "123";

  @Mock
  private IdGenerator idGenerator;

  private ChargingPointIdFactory chargingPointIdFactory;

  @Before
  public void setUp() {
    chargingPointIdFactory = new ChargingPointIdFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generate()).thenReturn(A_VALUE);
    ChargingPointId chargingPointId = chargingPointIdFactory.create();
    assertThat(chargingPointId.toString()).isEqualTo(A_VALUE);
  }
}
