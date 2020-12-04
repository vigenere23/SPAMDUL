package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingAccessLogIdFactoryTest {

  private static final Long A_VALUE = 123L;

  @Mock
  private IdGenerator<Long> idGenerator;

  private ParkingAccessLogIdFactory parkingAccessLogIdFactory;

  @Before
  public void setUp() {
    parkingAccessLogIdFactory = new ParkingAccessLogIdFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generateId()).thenReturn(A_VALUE);
    ParkingAccessLogId parkingAccessLogId = parkingAccessLogIdFactory.create();
    assertThat(parkingAccessLogId.toString()).isEqualTo(A_VALUE.toString());
  }
}
