package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassRepository;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.PeriodType;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
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
public class CampusAccessTest {


  private static final LicensePlate A_LICENSE_PLATE = new LicensePlate("license plate");
  private static final User A_USER = new User(new UserId(), "name", Gender.MALE, LocalDate.of(2010, 1, 1));
  private static final Car A_CAR = new Car(CarId.valueOf("1"),
                                    CarType.ECONOMIQUE,
                                    "brand",
                                    "model",
                                    2020,
                                    A_LICENSE_PLATE);
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final LocalDateTime A_WEDNESDAY_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 15, 0, 0);
  private static final LocalDateTime A_DATE_TIME_BEFORE = LocalDateTime.of(2019, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();

  private TimePeriod timePeriod;
  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                    A_USER,
                                    A_CAR,
                                    A_PERIOD_TYPE,
                                    timePeriod);
  }

  @Test
  public void givenDateTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldGrantAccess() {
    boolean grantedAccess = campusAccess.grantAccess(A_WEDNESDAY_IN_THE_MIDDLE);

    assertThat(grantedAccess).isTrue();
  }

  @Test
  public void givenDateNotTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldNotGrantAccess() {

    boolean grantedAccess = campusAccess.grantAccess(A_DATE_TIME_BEFORE);

    assertThat(grantedAccess).isFalse();
  }

  @Test
  public void givenPassPeriodIncluded_whenAssociatingSingleDayPerWeekPassOnSameDay_shouldSetPassCode() {
    final TimePeriod includedTimePeriod = new TimePeriod(A_START_DATE_TIME,
                                                         A_END_DATE_TIME,
                                                         TimePeriodDayOfWeek.WEDNESDAY);
    Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, includedTimePeriod);

    campusAccess.associatePass(pass);

    assertThat(campusAccess.getAssociatedPass()).isEqualTo(pass);
  }

  @Test(expected = PassAlreadyAssociatedException.class)
  public void givenPassAlreadyAssociated_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod includedTimePeriod = new TimePeriod(A_START_DATE_TIME,
                                                         A_END_DATE_TIME,
                                                         TimePeriodDayOfWeek.WEDNESDAY);
    Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, includedTimePeriod);
    campusAccess.associatePass(pass);

    campusAccess.associatePass(pass);
  }

  @Test(expected = PassNotAcceptedByAccessException.class)
  public void givenPassPeriodNotIncluded_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod notIncludedTimePeriod = new TimePeriod(A_START_DATE_TIME,
                                                               A_END_DATE_TIME,
                                                               TimePeriodDayOfWeek.FRIDAY);
    Pass pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, notIncludedTimePeriod);

    campusAccess.associatePass(pass);
  }

  @Test
  public void whenValidatingAssociatedLicensePlate_shouldCallCarToValidateLicensePlate() {
    Car car = mock(Car.class);
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                    A_USER,
                                    car,
                                    A_PERIOD_TYPE,
                                    timePeriod);

    campusAccess.validateAssociatedLicensePlate(A_LICENSE_PLATE);

    verify(car, times(1)).validateLicensePlate(A_LICENSE_PLATE);
  }

  @Test
  public void givenCampusAccessWithAssociatedPass_whenGetParkingZone_shouldReturnPassParkingZone() {
    Pass pass = new Pass(A_PASS_CODE, ParkingZone.ZONE_1, A_TIME_PERIOD);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, A_PERIOD_TYPE, A_TIME_PERIOD);
    campusAccess.associatePass(pass);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ZONE_1);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsHourly_whenGetParkingZone_shouldReturnParkingZoneAll() {
    PeriodType periodType = PeriodType.HOURLY;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsSingleDay_whenGetParkingZone_shouldReturnParkingZoneAll() {
    PeriodType periodType = PeriodType.SINGLE_DAY;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsOneSemester_whenGetParkingZone_shouldReturnParkingZoneFree() {
    PeriodType periodType = PeriodType.ONE_SEMESTER;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.FREE);
  }
}