package ca.ulaval.glo4003.spamdul.infrastructure.db.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotFoundException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

public class InMemoryPassRepositoryTest {
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000,1,1,0,0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001,1,1,0,0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.MONDAY);
  private static final Pass A_PASS = new Pass(new PassCode(), ParkingZone.ZONE_1, A_TIME_PERIOD);
  private InMemoryPassRepository passRepositoryInMemory;

  @Before
  public void setUp() {
    passRepositoryInMemory = new InMemoryPassRepository();
  }

  @Test
  public void givenASavedPass_whenFindingPassByPassCode_shouldReturnRightPass() {
    passRepositoryInMemory.save(A_PASS);

    assertThat(passRepositoryInMemory.findByPassCode(A_PASS.getPassCode())).isEqualTo(A_PASS);
  }

  @Test(expected = PassNotFoundException.class)
  public void whenFindingPassByPassCodeAndNotFound_shouldThrow() {
    passRepositoryInMemory.findByPassCode(A_PASS.getPassCode());
  }
}
