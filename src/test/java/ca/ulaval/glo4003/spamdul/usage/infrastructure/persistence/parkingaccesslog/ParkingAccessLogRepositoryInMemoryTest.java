package ca.ulaval.glo4003.spamdul.usage.infrastructure.persistence.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLog;
import ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog.ParkingAccessLogId;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParkingAccessLogRepositoryInMemoryTest {

  private static final ParkingAccessLogId PARKING_ACCESS_LOG_ID = ParkingAccessLogId.valueOf("123");
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final LocalDate A_DATE = LocalDate.of(2015, 11, 24);

  private final ParkingAccessLogRepositoryInMemory parkingAccessLogRepository = new ParkingAccessLogRepositoryInMemory();

  @Before
  public void setUp() {
    parkingAccessLogRepository.clear();
  }

  @Test
  public void givenInitialized_whenFindingAll_shouldReturnEmptyList() {
    List<ParkingAccessLog> logs = parkingAccessLogRepository.find().getAll();
    assertThat(logs).isEmpty();
  }

  @Test
  public void givenParkingAccessLog_whenSaving_shouldBeAbleToRetrieveThatLog() {
    ParkingAccessLog parkingAccessLog = new ParkingAccessLog(PARKING_ACCESS_LOG_ID, A_PARKING_ZONE, A_DATE);
    parkingAccessLogRepository.save(parkingAccessLog);

    List<ParkingAccessLog> logs = parkingAccessLogRepository.find().getAll();

    assertThat(logs).containsExactly(parkingAccessLog);
  }
}
