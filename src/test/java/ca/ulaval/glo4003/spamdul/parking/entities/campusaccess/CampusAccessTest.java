package ca.ulaval.glo4003.spamdul.parking.entities.campusaccess;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDayOfWeek;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CampusAccessTest {

  private static final ParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private static final LocalDateTime A_WEDNESDAY_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 15, 0, 0);
  private static final LocalDateTime A_DATE_TIME_BEFORE = LocalDateTime.of(2019, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.ALL);
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(2020, 1, 1, 1, 1);
  public static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.FRIDAY;
  private static final CampusAccessCode A_CAMPUS_ACCESS_CODE = CampusAccessCode.valueOf("123");

  private TimePeriod timePeriod;
  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    campusAccess = new CampusAccess(CampusAccessCode.valueOf("123"),
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
    CarParkingPass parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, includedTimePeriod);

    campusAccess.associatePass(parkingPass);

    assertThat(campusAccess.getAssociatedPass()).isEqualTo(parkingPass);
  }

  @Test(expected = PassAlreadyAssociatedException.class)
  public void givenPassAlreadyAssociated_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod includedTimePeriod = new TimePeriod(A_START_DATE_TIME,
                                                         A_END_DATE_TIME,
                                                         TimePeriodDayOfWeek.WEDNESDAY);
    CarParkingPass parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, includedTimePeriod);
    campusAccess.associatePass(parkingPass);

    campusAccess.associatePass(parkingPass);
  }

  @Test(expected = PassNotAcceptedByAccessException.class)
  public void givenPassPeriodNotIncluded_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod notIncludedTimePeriod = new TimePeriod(A_START_DATE_TIME,
                                                            A_END_DATE_TIME,
                                                            TimePeriodDayOfWeek.FRIDAY);
    CarParkingPass parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, notIncludedTimePeriod);

    campusAccess.associatePass(parkingPass);
  }

  @Test
  public void givenCampusAccessWithAssociatedPass_whenGetParkingZone_shouldReturnPassParkingZone() {
    CarParkingPass parkingPass = new CarParkingPass(A_PASS_CODE, ParkingZone.ZONE_1, A_TIME_PERIOD);
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);
    campusAccess.associatePass(parkingPass);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.ZONE_1);
  }

  @Test
  public void givenCampusAccessWithoutAssociatedPassA_whenGetParkingZone_shouldReturnParkingZoneFree() {
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);

    ParkingZone parkingZone = campusAccess.getParkingZone();

    assertThat(parkingZone).isEqualTo(ParkingZone.FREE);
  }

  @Test
  public void whenCheckingIfCanParkInZone_shouldDelegateToPass() {
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);
    CarParkingPass pass = mock(CarParkingPass.class);
    TimePeriod timePeriod = mock(TimePeriod.class);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(timePeriod.includedIn(A_TIME_PERIOD)).thenReturn(true);
    campusAccess.associatePass(pass);

    campusAccess.canParkInZone(A_PARKING_ZONE);

    verify(pass).isAValidParkingZone(A_PARKING_ZONE);
  }

  @Test
  public void whenCheckingIfHasParkingPassBoundingInstant_shouldDelegateCampusAccess() {
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);
    CarParkingPass pass = mock(CarParkingPass.class);
    TimePeriod timePeriod = mock(TimePeriod.class);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(timePeriod.includedIn(A_TIME_PERIOD)).thenReturn(true);
    campusAccess.associatePass(pass);

    campusAccess.hasParkingPassBoundingInstant(A_LOCAL_DATE_TIME);

    verify(pass).doesBoundInstant(A_LOCAL_DATE_TIME);
  }

  @Test
  public void whenCheckingIfCanParkOnDayOfWeek_shouldDelegateToCampusAccess() {
    campusAccess = new CampusAccess(A_CAMPUS_ACCESS_CODE, A_TIME_PERIOD);
    CarParkingPass pass = mock(CarParkingPass.class);
    TimePeriod timePeriod = mock(TimePeriod.class);
    when(pass.getTimePeriod()).thenReturn(timePeriod);
    when(timePeriod.includedIn(A_TIME_PERIOD)).thenReturn(true);
    campusAccess.associatePass(pass);

    pass.isValidOnThisDayOfWeek(A_DAY_OF_WEEK);

    verify(pass).isValidOnThisDayOfWeek(A_DAY_OF_WEEK);
  }
}
