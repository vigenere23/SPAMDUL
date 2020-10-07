package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.*;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.CampusAccessService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PassServiceTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000,1,1,0,0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001,1,1,0,0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, ca.ulaval.glo4003.spamdul.entity.timeperiod.DayOfWeek.MONDAY);
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);

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
    when(passFactory.create(A_PARKING_ZONE, A_TIME_PERIOD_DTO)).thenReturn(pass);
  }

  @Test
  public void whenCreatingPass_shouldCallFactoryToCreateNewPass() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_TIME_PERIOD_DTO);

    verify(passFactory).create(A_PARKING_ZONE, A_TIME_PERIOD_DTO);
  }

  public void whenCreatingPass_shouldCallCampusAccessServiceToAssociatePassToAccess() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_TIME_PERIOD_DTO);

    verify(campusAccessService).associatePassToCampusAccess(A_CAMPUS_ACCESS_CODE, A_PASS_CODE, A_TIME_PERIOD);
  }

  @Test
  public void whenCreatingPass_shouldAddPassToRepository() {
    passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_TIME_PERIOD_DTO);

    verify(passRepository).save(pass);
  }

  @Test
  public void whenCreatingPass_thenShouldReturnPassCode() {
   PassCode  passCode = passService.createPass(A_CAMPUS_ACCESS_CODE, A_PARKING_ZONE, A_TIME_PERIOD_DTO);

   assertThat(passCode).isEqualTo(A_PASS_CODE);
  }
}
