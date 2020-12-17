package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingAccessLoggerTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final LocalDate A_DATE = LocalDate.of(2011, 11, 2);

  private ParkingAccessLogger parkingAccessLogger;

  @Mock
  private ParkingAccessLogFactory parkingAccessLogFactory;
  @Mock
  private ParkingAccessLogRepository parkingAccessLogRepository;
  @Mock
  private ParkingAccessLog A_PARKING_ACCESS_LOG;

  @Before
  public void setUp() {
    parkingAccessLogger = new ParkingAccessLogger(parkingAccessLogFactory, parkingAccessLogRepository);
  }

  @Test
  public void whenHandlingAccessGranted_shouldCreateAndSaveNewParkingAccessLog() {
    when(parkingAccessLogFactory.create(A_PARKING_ZONE, A_DATE)).thenReturn(A_PARKING_ACCESS_LOG);
    parkingAccessLogger.handleAccessGranted(A_PARKING_ZONE, A_DATE);
    verify(parkingAccessLogRepository).save(A_PARKING_ACCESS_LOG);
  }
}
