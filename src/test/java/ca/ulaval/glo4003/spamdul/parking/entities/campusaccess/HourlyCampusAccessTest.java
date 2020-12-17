package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

public class HourlyCampusAccessTest {

  private static final BigDecimal NUMBER_OF_HOURS = BigDecimal.ONE;
  private static final LocalDateTime DATE_TIME_OF_ACCESS = LocalDateTime.of(2020, 1, 15, 0, 0);
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");

  private HourlyCampusAccess hourlyCampusAccess;

  @Before
  public void setUp() throws Exception {
    hourlyCampusAccess = new HourlyCampusAccess(A_CAMPUS_ACCESS_CODE,
                                                NUMBER_OF_HOURS);
  }

  @Test
  public void whenCreated_shouldHaveNullTimePeriod() {
    assertThat(hourlyCampusAccess.timePeriod).isNull();
  }

  @Test
  public void givenFirstAccess_whenVerifyingIfGrantedAccess_shouldCreateNewTimePeriodAndSetItAsAttribute() {
    hourlyCampusAccess.grantAccess(DATE_TIME_OF_ACCESS);

    assertThat(hourlyCampusAccess.timePeriod.getEndDateTime())
            .isEqualTo(DATE_TIME_OF_ACCESS.plusHours(NUMBER_OF_HOURS.longValue()));
  }

  @Test
  public void whenGettingCorrespondingParkingZone_shouldReturnAll() {
    ParkingZone parkingZone = hourlyCampusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }

}
