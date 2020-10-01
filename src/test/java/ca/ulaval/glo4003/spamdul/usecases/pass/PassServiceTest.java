package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.*;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final PassType A_PASS_TYPE = PassType.MONTHLY;
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.FRIDAY;
  private static final PassCode A_PASS_CODE = new PassCode();

  private Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

  @Mock
  private PassFactory passFactory;
  @Mock
  private PassRepository passRepository;
  @Mock
  private CampusAccessService campusAccessService;

  private PassService passService;

  @Before
  public void setUp() {
    passService = new PassService(passRepository, passFactory, campusAccessService);
    when(passFactory.create(A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK)).thenReturn(pass);
  }

  @Test
  public void whenCreatingPass_shouldCallFactoryToCreateNewPass() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(passFactory).create(A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);
  }

  public void whenCreatingPass_shouldCallCampusAccessServiceToAssociatePassToAccess() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(campusAccessService).associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_PASS_TYPE,
            A_DAY_OF_WEEK);
  }

  @Test
  public void whenCreatingPass_shouldAddPassToRepository() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

    verify(passRepository).save(pass);
  }

  @Test
  public void whenCreatingPass_thenShouldReturnPassCode() {
    PassCode  passCode = passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_PASS_TYPE, A_DAY_OF_WEEK);

    assertThat(passCode).isEqualTo(A_PASS_CODE);
  }
}
