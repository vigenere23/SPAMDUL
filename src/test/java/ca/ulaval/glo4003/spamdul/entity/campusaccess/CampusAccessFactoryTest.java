package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.*;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessFactoryTest {

  private final UserId A_USER_ID = new UserId();
  private final String A_NAME = "name";
  private final Gender A_GENDER = Gender.MALE;
  private final LocalDate A_BIRTHDAY_DATE = LocalDate.of(2010, 1, 1);
  private final User A_USER = new User(A_USER_ID, A_NAME, A_GENDER, A_BIRTHDAY_DATE);
  private final String A_CAR_BRAND = "brand";
  private final String A_CAR_MODEL = "model";
  private final String A_LICENSE_PLATE_STRING = "license plate";
  private final LicensePlate A_LICENSE_PLATE = new LicensePlate(A_LICENSE_PLATE_STRING);
  private final CarId A_CAR_ID = CarId.valueOf("1");
  private final CarType A_CAR_TYPE = CarType.ECONOMIQUE;
  private final int A_YEAR = 2020;
  private final Car A_CAR = new Car(A_CAR_ID, A_CAR_TYPE, A_CAR_BRAND, A_CAR_MODEL, A_YEAR, A_LICENSE_PLATE);
  private final TimePeriodDto A_TIME_PERIOD_DTO = new TimePeriodDto();
  private final TimePeriod A_TIME_PERIOD = new TimePeriod(LocalDateTime.of(2020, 1, 1, 0, 0),
                                                          LocalDateTime.of(2020, 1, 2, 0, 0),
                                                          TimePeriodDayOfWeek.MONDAY);

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

    campusAccessFactory.create(A_USER, A_CAR, A_TIME_PERIOD_DTO);

    verify(timePeriodFactory).createTimePeriod(A_TIME_PERIOD_DTO);
  }

  @Test
  public void whenCreatingCampusAccess_shouldCreateCamusAccess() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);

    CampusAccess campusAccess = campusAccessFactory.create(A_USER, A_CAR, A_TIME_PERIOD_DTO);

    assertThat(campusAccess.getUser()).isEqualTo(A_USER);
    assertThat(campusAccess.getCar()).isEqualTo(A_CAR);
    assertThat(campusAccess.getCampusAccessCode()).isNotNull();
    assertThat(campusAccess.getTimePeriod()).isEqualTo(A_TIME_PERIOD);
  }

  @Test
  public void whenHourlyCampusAccessWithHourlyTimePeriodDto_shouldCreateHourlyCamusAccess() {
    when(timePeriodFactory.createTimePeriod(A_TIME_PERIOD_DTO)).thenReturn(A_TIME_PERIOD);
    A_TIME_PERIOD_DTO.periodType = PeriodType.HOURLY;

    CampusAccess campusAccess = campusAccessFactory.create(A_USER, A_CAR, A_TIME_PERIOD_DTO);

    assertThat(campusAccess).isInstanceOf(HourlyCampusAccess.class);
  }
}