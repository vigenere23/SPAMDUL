package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import com.google.common.truth.Truth;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class ParkingPassTest {

  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(2020, 1, 1, 1, 1);
  public static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.FRIDAY;
  private final CarParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("1");
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_1;
  private final ParkingZone SAME_PARKING_ZONE = ParkingZone.ZONE_1;
  private final ParkingZone ANOTHER_PARKING_ZONE = ParkingZone.ZONE_2;
  private final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_THE_WEEK = TimePeriodDayOfWeek.MONDAY;
  private final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                          A_END_DATE_TIME,
                                                          A_TIME_PERIOD_DAY_OF_THE_WEEK);

  private ParkingPass parkingPass;

  @Before
  public void setUp() throws Exception {
    parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, A_TIME_PERIOD);
  }

  @Test
  public void givenTheSameParkingZone_whenValidatingParkingZone_shouldReturnTrue() {
    boolean isParkingZoneValid = parkingPass.isAValidParkingZone(SAME_PARKING_ZONE);

    Truth.assertThat(isParkingZoneValid).isTrue();
  }

  @Test
  public void givenAnotherParkingZone_whenValidatingParkingZone_shouldReturnFalse() {
    boolean isParkingZoneValid = parkingPass.isAValidParkingZone(ANOTHER_PARKING_ZONE);

    Truth.assertThat(isParkingZoneValid).isFalse();
  }

  @Test
  public void whenCheckingIfBoundsInstant_shouldDelegateToTimePeriod() {
    TimePeriod timePeriod = mock(TimePeriod.class);
    parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);

    parkingPass.doesBoundInstant(A_LOCAL_DATE_TIME);

    verify(timePeriod).bounds(A_LOCAL_DATE_TIME);
  }

  @Test
  public void whenCheckingIfValidOnDayOfWeek_shouldDelegateToTimePeriod() {
    TimePeriod timePeriod = mock(TimePeriod.class);
    parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);

    parkingPass.isValidOnThisDayOfWeek(A_DAY_OF_WEEK);

    verify(timePeriod).mayIncludeThisDayOfWeek(A_DAY_OF_WEEK);
  }
}