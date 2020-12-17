package ca.ulaval.glo4003.spamdul.usage.entities.parkingaccesslog;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import java.time.LocalDate;
import org.junit.Test;

public class ParkingAccessLogTest {

  private static final ParkingAccessLogId AN_ID = ParkingAccessLogId.valueOf("123");
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_3;
  private static final LocalDate A_DATE = LocalDate.of(2011, 2, 1);

  @Test
  public void whenCreating_itAssignsFields() {
    ParkingAccessLog parkingAccessLog = new ParkingAccessLog(AN_ID, A_PARKING_ZONE, A_DATE);
    assertThat(parkingAccessLog.getId()).isEqualTo(AN_ID);
    assertThat(parkingAccessLog.getZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(parkingAccessLog.getAccessDate()).isEqualTo(A_DATE);
  }
}
