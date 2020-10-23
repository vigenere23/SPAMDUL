package ca.ulaval.glo4003.spamdul.entity.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

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
public class PassFactoryTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);

  @Mock
  private TimePeriodFactory timePeriodFactory;

  private PassFactory passFactory;

  @Test
  public void givenSingleDayPerWeekType_whenCreatingPass_shouldCreatePassWithRightInfo() {
    passFactory = new PassFactory(timePeriodFactory);
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);

    Pass pass = passFactory.create(A_PARKING_ZONE, A_TIME_PERIOD_DTO);

    assertThat(pass.getParkingZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(pass.getTimePeriod()).isEqualTo(A_TIME_PERIOD);
    assertThat(pass.getPassCode()).isNotNull();
  }
}
