package ca.ulaval.glo4003.spamdul.charging.entities.chargingpoint;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPointFactoryTest {

  private static final ChargingPointId AN_ID = ChargingPointId.valueOf("123");

  private ChargingPointFactory chargingPointFactory;

  @Mock
  private ChargingPointIdFactory chargingPointIdFactory;

  @Before
  public void setUp() {
    chargingPointFactory = new ChargingPointFactory(chargingPointIdFactory);
  }

  @Test
  public void whenCreating_shouldReturnNewChargingPoint() {
    when(chargingPointIdFactory.create()).thenReturn(AN_ID);
    ChargingPoint chargingPoint = chargingPointFactory.create();
    assertThat(chargingPoint.getId()).isEqualTo(AN_ID);
  }
}
