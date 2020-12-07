package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassPeriodTypeException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassFactoryTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final PeriodType A_VALID_PASS_PERIOD_TYPE = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;

  private TimePeriodDto timePeriodDto = new TimePeriodDto();
  @Mock
  private TimePeriodFactory timePeriodFactory;

  private PassFactory passFactory;

  @Before
  public void setUpDependencies() {
    passFactory = new PassFactory(timePeriodFactory);
  }

  @Test
  public void givenValidPeriodType_whenCreatingPass_shouldCreatePassWithRightInfo() {
    timePeriodDto.periodType = A_VALID_PASS_PERIOD_TYPE;
    when(timePeriodFactory.createTimePeriod(timePeriodDto)).thenReturn(A_TIME_PERIOD);

    Pass pass = passFactory.create(A_PARKING_ZONE, timePeriodDto);

    assertThat(pass.getParkingZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(pass.getTimePeriod()).isEqualTo(A_TIME_PERIOD);
    assertThat(pass.getPassCode()).isNotNull();
  }

  @Test(expected = InvalidPassPeriodTypeException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldThrowInvalidPeriodException() {
    timePeriodDto.periodType = PeriodType.SINGLE_DAY;

    passFactory.create(A_PARKING_ZONE, timePeriodDto);
  }
}
