package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.car.Car;
import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.car.CarType;
import ca.ulaval.glo4003.spamdul.entity.car.LicensePlate;
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
  private static final PassCode A_PASS_CODE = new PassCode();
  private static final PeriodType A_PERIOD_TYPE = PeriodType.ONE_SEMESTER;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime AN_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final LocalDateTime A_WEDNESDAY_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 15, 0, 0);
  private static final LocalDateTime A_DATE_TIME_BEFORE = LocalDateTime.of(2019, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME, AN_END_DATE_TIME, TimePeriodDayOfWeek.ALL);
  public static final CampusAccessCode A_CAMPUS_ACCESS_CODE = new CampusAccessCode();

  @Mock
  PassRepository passRepository;

  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                    A_USER,
                                    A_CAR,
                                    A_PERIOD_TYPE,
                                    A_TIME_PERIOD);
  }

  @Test
  public void givenDateTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldGrantAccess() {
    boolean grantedAccess = campusAccess.isAccessGranted(A_WEDNESDAY_IN_THE_MIDDLE);

    assertThat(grantedAccess).isTrue();
  }

  @Test
  public void givenDateNotTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldNotGrantAccess() {

    boolean grantedAccess = campusAccess.isAccessGranted(A_DATE_TIME_BEFORE);

    assertThat(grantedAccess).isFalse();
  }

  @Test
  public void givenPassPeriodIncluded_whenAssociatingSingleDayPerWeekPassOnSameDay_shouldSetPassCode() {
    final TimePeriod INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                           AN_END_DATE_TIME,
                                                           TimePeriodDayOfWeek.WEDNESDAY);

    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }

  @Test(expected = PassAlreadyAssociatedException.class)
  public void givenPassAlreadyAssociated_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                           AN_END_DATE_TIME,
                                                           TimePeriodDayOfWeek.WEDNESDAY);
    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);

    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);
  }

  @Test(expected = PassNotAcceptedByAccessException.class)
  public void givenPassPeriodNotIncluded_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod NOT_INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                               AN_END_DATE_TIME,
                                                               TimePeriodDayOfWeek.FRIDAY);

    campusAccess.associatePass(A_PASS_CODE, NOT_INCLUDED_TIME_PERIOD);
  }

  @Test
  public void whenValidatingAssociatedLicensePlate_shouldCallCarToValidateLicensePlate() {
    Car car = mock(Car.class);
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                    A_USER,
                                    car,
                                    A_PERIOD_TYPE,
                                    A_TIME_PERIOD);

    campusAccess.validateAssociatedLicensePlate(A_LICENSE_PLATE);

    verify(car, times(1)).validateLicensePlate(A_LICENSE_PLATE);
  }

  @Test
  public void givenCampusAccessWithAssociatedPass_whenGetParkingZone_shouldReturnPassParkingZone() {
    Pass pass = new Pass(A_PASS_CODE, ParkingZone.ZONE_1, A_TIME_PERIOD);
    when(passRepository.findByPassCode(A_PASS_CODE)).thenReturn(pass);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, A_PERIOD_TYPE, A_TIME_PERIOD);
    campusAccess.associatePass(pass.getPassCode(), A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone(passRepository);

    assertThat(parkingZone).isEqualTo(ParkingZone.ZONE_1);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsHourly_whenGetParkingZone_shouldReturnParkingZoneAll() {
    PeriodType periodType = PeriodType.HOURLY;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone(passRepository);

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsSingleDay_whenGetParkingZone_shouldReturnParkingZoneAll() {
    PeriodType periodType = PeriodType.SINGLE_DAY;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone(passRepository);

    assertThat(parkingZone).isEqualTo(ParkingZone.ALL);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassAndPeriodTypeIsOneSemester_whenGetParkingZone_shouldReturnParkingZoneFree() {
    PeriodType periodType = PeriodType.ONE_SEMESTER;
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_USER, A_CAR, periodType, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone(passRepository);

    assertThat(parkingZone).isEqualTo(ParkingZone.FREE);
  }
}