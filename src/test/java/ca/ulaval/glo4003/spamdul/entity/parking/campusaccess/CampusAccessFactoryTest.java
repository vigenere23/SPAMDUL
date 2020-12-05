package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodFactory;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessFactoryTest {

  private final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private final TimePeriod A_TIME_PERIOD = new TimePeriod(LocalDateTime.of(2020, 1, 1, 0, 0),
                                                          LocalDateTime.of(2020, 1, 2, 0, 0),
                                                          TimePeriodDayOfWeek.MONDAY);
  private final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");

  @Mock
  private TimePeriodFactory timePeriodFactory;
  @Mock
  private CampusAccessCodeFactory campusAccessCodeFactory;

  private CampusAccessFactory campusAccessFactory;

  @Before
  public void setUp() throws Exception {
    campusAccessFactory = new CampusAccessFactory(campusAccessCodeFactory, timePeriodFactory);
  }

  @Test
  public void whenCreatingCampusAccess_shouldCallTimePeriodFactoryCreate() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);

    campusAccessFactory.create(A_TIME_PERIOD_DTO);

    verify(timePeriodFactory).createTimePeriod(A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingCampusAccess_shouldCreateCamusAccess() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);
    when(campusAccessCodeFactory.create()).thenReturn(A_CAMPUS_ACCESS_CODE);

    CampusAccess campusAccess = campusAccessFactory.create(A_TIME_PERIOD_DTO);

    assertThat(campusAccess.getCampusAccessCode()).isNotNull();
    assertThat(campusAccess.getTimePeriod()).isEqualTo(A_TIME_PERIOD);
  }

  @Test
  public void whenHourlyCampusAccessWithHourlyTimePeriodDto_shouldCreateHourlyCamusAccess() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);
    A_TIME_PERIOD_DTO.periodType = PeriodType.HOURLY;

    CampusAccess campusAccess = campusAccessFactory.create(A_TIME_PERIOD_DTO);

    assertThat(campusAccess).isInstanceOf(HourlyCampusAccess.class);
  }
}
