package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.DayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
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
public class CampusAccessFactoryTest {

  private final UserId A_USER_ID = new UserId();
  private final CarId A_CAR_ID = new CarId();
  private final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private final TimePeriod A_TIME_PERIOD = new TimePeriod(LocalDateTime.of(2020,1,1,0,0),
          LocalDateTime.of(2020,1,2,0,0),
          DayOfWeek.MONDAY);

  @Mock
  private TimePeriodFactory timePeriodFactory;

  private CampusAccessFactory campusAccessFactory;

  @Before
  public void setUp() throws Exception {
    campusAccessFactory = new CampusAccessFactory(timePeriodFactory);
  }

  @Test
  public void whenCreatingCampusAccess_shouldCallTimePeriodFactoryCreate() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);

    campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_TIME_PERIOD_DTO);

    verify(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO));
  }

  @Test
  public void whenCreatingCampusAccess_shouldCreateCamusAccess() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);

    CampusAccess campusAccess = campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_TIME_PERIOD_DTO);

    assertThat(campusAccess.getUserId()).isEqualTo(A_USER_ID);
    assertThat(campusAccess.getCarId()).isEqualTo(A_CAR_ID);
    assertThat(campusAccess.getCampusAccessCode()).isNotNull();
    assertThat(campusAccess.getTimePeriod()).isEqualTo(A_TIME_PERIOD);
  }
  // TODO: those tests will go in the assembler!
/*
  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSaturdayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, DayOfWeek.SATURDAY);
  }

  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSundayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, DayOfWeek.SUNDAY);
  }*/
}