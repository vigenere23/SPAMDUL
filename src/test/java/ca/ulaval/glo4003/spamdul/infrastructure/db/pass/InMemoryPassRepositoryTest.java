package ca.ulaval.glo4003.spamdul.infrastructure.db.pass;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;

import static com.google.common.truth.Truth.assertThat;

public class InMemoryPassRepositoryTest {

  private static final Pass A_PASS = new Pass(new PassCode(), ParkingZone.ZONE_1, PassType.MONTHLY, DayOfWeek.MONDAY);
  private InMemoryPassRepository passRepositoryInMemory;

  @Before
  public void setUp() {
    passRepositoryInMemory = new InMemoryPassRepository();
    passRepositoryInMemory.save(A_PASS);
  }

  @Test
  public void givenASavedPass_whenFindingPassByPassCode_thenShouldReturnRightPass() {

    assertThat(passRepositoryInMemory.findByPassCode(A_PASS.getPassCode())).isEqualTo(A_PASS);
  }
}
