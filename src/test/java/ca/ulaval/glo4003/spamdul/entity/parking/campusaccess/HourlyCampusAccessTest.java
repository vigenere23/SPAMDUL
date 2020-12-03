package ca.ulaval.glo4003.spamdul.entity.parking.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class HourlyCampusAccessTest {

  private static final BigDecimal NUMBER_OF_HOURS = BigDecimal.ONE;
  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_UNRESTRICTED_END_DATE_TIME = LocalDateTime.MAX;
  private static final LocalDateTime DATE_TIME_OF_ACCESS = LocalDateTime.of(2020, 1, 15, 0, 0);

  private TimePeriod timePeriod;
  private HourlyCampusAccess hourlyCampusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_UNRESTRICTED_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    hourlyCampusAccess = new HourlyCampusAccess(new CampusAccessCode(),
                                                A_PERIOD_TYPE,
                                                timePeriod,
                                                NUMBER_OF_HOURS);
  }

  @Test
  public void givenFirstAccess_whenVerifyingIfGrantedAccess_shouldRestrictTimePeriod() {
    hourlyCampusAccess.grantAccess(DATE_TIME_OF_ACCESS);

    assertThat(timePeriod.getEndDateTime()).isEqualTo(DATE_TIME_OF_ACCESS.plusHours(NUMBER_OF_HOURS.longValue()));
  }

}
