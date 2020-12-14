package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.exceptions.InvalidPassPeriodTypeException;
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
public class ParkingPassFactoryTest {

  private static final ParkingPassCode BIKE_PARKING_PASS_CODE = BikeParkingPassCode.valueOf("1234");
  private static final ParkingPassCode CAR_PARKING_PASS_CODE = CarParkingPassCode.valueOf("1234");
  private static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 0, 0);
  private static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2001, 1, 1, 0, 0);
  private static final TimePeriod A_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                                 A_END_DATE_TIME,
                                                                 TimePeriodDayOfWeek.MONDAY);
  private static final PeriodType A_VALID_PASS_PERIOD_TYPE = PeriodType.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
  public static final ParkingZone A_CAR_PARKING_ZONE = ParkingZone.ZONE_1;

  private final TimePeriodDto timePeriodDto = new TimePeriodDto();
  @Mock
  private TimePeriodFactory timePeriodFactory;
  @Mock
  private ParkingPassCodeFactory parkingPassCodeFactory;

  private ParkingPassFactory parkingPassFactory;

  @Before
  public void setUp() {
    timePeriodDto.periodType = A_VALID_PASS_PERIOD_TYPE;
    when(timePeriodFactory.createTimePeriod(timePeriodDto)).thenReturn(A_TIME_PERIOD);
    parkingPassFactory = new ParkingPassFactory(parkingPassCodeFactory, timePeriodFactory);
  }

  @Test
  public void givenABikeParkingZone_whenCreatingParkingPass_shouldCreateBikeParkingPass() {
    when(parkingPassCodeFactory.create(any(ParkingZone.class))).thenReturn(BIKE_PARKING_PASS_CODE);

    ParkingPass parkingPass = parkingPassFactory.create(ParkingZone.ZONE_BIKE, timePeriodDto);

    assertThat(parkingPass.getClass()).isEqualTo(BikeParkingPass.class);
  }

  @Test
  public void givenACarParkingZone_whenCreatingParkingPass_shouldCreateCarParkingPass() {
    when(parkingPassCodeFactory.create(any(ParkingZone.class))).thenReturn(CAR_PARKING_PASS_CODE);

    ParkingPass parkingPass = parkingPassFactory.create(A_CAR_PARKING_ZONE, timePeriodDto);

    assertThat(parkingPass.getClass()).isEqualTo(CarParkingPass.class);
  }

  @Test(expected = InvalidPassPeriodTypeException.class)
  public void givenAnInvalidPeriod_whenAssemblingFromRequest_shouldThrowInvalidPeriodException() {
    timePeriodDto.periodType = PeriodType.SINGLE_DAY;

    parkingPassFactory.create(A_CAR_PARKING_ZONE, timePeriodDto);
  }
}
