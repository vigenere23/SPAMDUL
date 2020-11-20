package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

public class HourlyCampusAccessTest {

  private static final BigDecimal NUMBER_OF_HOURS = BigDecimal.ONE;
  private static final LicensePlate A_LICENSE_PLATE = new LicensePlate("license plate");
  private static final User A_USER = new User(new UserId(), "name", Gender.MALE, LocalDate.of(2010, 1, 1));
  private static final Car A_CAR = new Car(CarId.valueOf("1"),
          CarType.ECONOMIQUE,
          "brand",
          "model",
          2020,
          A_LICENSE_PLATE);

  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_UNRESTRICTED_END_DATE_TIME = LocalDateTime.MAX;
  private static final LocalDateTime DATE_TIME_OF_ACCESS = LocalDateTime.of(2020, 1, 15, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
          A_UNRESTRICTED_END_DATE_TIME,
          TimePeriodDayOfWeek.ALL);
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();

  private TimePeriod timePeriod;
  private HourlyCampusAccess hourlyCampusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_UNRESTRICTED_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    hourlyCampusAccess = new HourlyCampusAccess(new CampusAccessCode(),
            A_USER,
            A_CAR,
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
