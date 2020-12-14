package ca.ulaval.glo4003.spamdul.entity.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingAccessLogFactoryTest {

  private static final ParkingAccessLogId AN_ID = ParkingAccessLogId.valueOf("123");
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_3;
  private static final LocalDate A_DATE = LocalDate.of(2011, 2, 1);

  private ParkingAccessLogFactory parkingAccessLogFactory;

  @Mock
  private ParkingAccessLogIdFactory parkingAccessLogIdFactory;

  @Before
  public void setUp() {
    parkingAccessLogFactory = new ParkingAccessLogFactory(parkingAccessLogIdFactory);
  }

  @Test
  public void whenCreating_shouldCreate() {
    when(parkingAccessLogIdFactory.create()).thenReturn(AN_ID);
    ParkingAccessLog parkingAccessLog = parkingAccessLogFactory.create(A_PARKING_ZONE, A_DATE);
    assertThat(parkingAccessLog.getId()).isEqualTo(AN_ID);
    assertThat(parkingAccessLog.getZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(parkingAccessLog.getAccessDate()).isEqualTo(A_DATE);
  }
}
