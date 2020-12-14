package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

public class DailyCampusAccessTest {
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_UNRESTRICTED_END_DATE_TIME = LocalDateTime.MAX;
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");

  private TimePeriod timePeriod;
  private DailyCampusAccess dailyCampusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_UNRESTRICTED_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    dailyCampusAccess = new DailyCampusAccess(A_CAMPUS_ACCESS_CODE,
            timePeriod);
  }

  @Test
  public void whenGettingCorrespondingParkingZone_shouldReturnAll() {
    ParkingZone parkingZone = dailyCampusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }
}
