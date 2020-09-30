package ca.ulaval.glo4003.spamdul.usecases.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassFactory;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {

  private final UserId A_USER_ID = new UserId();
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;


  private final Pass A_PASS = new Pass(new PassCode(), A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);
  private PassDto passDto = new PassDto();
  @Mock
  private PassFactory passFactory;
  @Mock
  private PassRepository passRepository;

  private PassService passService;

  @Before
  public void setUp() {
    passDto.userId = A_USER_ID;
    passDto.parkingZone = A_PARKING_ZONE;
    passDto.passType = A_PASS_TYPE;

    passService = new PassService(passRepository, passFactory);
    when(passFactory.create(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE)).thenReturn(A_PASS);

  }

  @Test
  public void whenSellingPass_shouldCallFactoryToCreateNewPass() {
    passService.createPass(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);

    verify(passFactory).create(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);
  }

  @Test
  public void whenSellingPass_shouldAddPassToRepository() {
    passService.createPass(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);

    verify(passRepository).save(A_PASS);
  }

  @Test
  public void whenCreatingPass_thenShouldReturnRightPassCode() {


    assertThat(passService.createPass(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE)).isEqualTo(A_PASS.getPassCode());
  }
}
