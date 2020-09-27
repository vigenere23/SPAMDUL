package ca.ulaval.glo4003.spamdul.infrastructure.db.pass;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;

public class PassRepositoryInMemoryTest {

  private final Pass A_PASS = new Pass(new PassCode(), new UserId(), ParkingZone.ZONE_1, PassType.MONTHLY);
  private final Pass A_SECOND_PASS = new Pass(new PassCode(), new UserId(), ParkingZone.ZONE_2, PassType.ONE_SEMESTER);
  private PassRepository passRepositoryInMemory;

  @Before
  public void setUp() {
    passRepositoryInMemory = new PassRepositoryInMemory();
    passRepositoryInMemory.save(A_PASS);
  }

  @Test
  public void givenASavedPass_whenFindingPassByPassCode_thenShouldReturnRightPass() {

    assertThat(passRepositoryInMemory.findByPassCode(A_PASS.getPassCode())).isEqualTo(A_PASS);
  }

  @Test
  public void whenSavingPass_thenPassShouldBeAddedToRepository() {
    passRepositoryInMemory.save(A_SECOND_PASS);

    assertThat(passRepositoryInMemory.findByPassCode(A_SECOND_PASS.getPassCode())).isEqualTo(A_SECOND_PASS);
  }
}
