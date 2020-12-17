package ca.ulaval.glo4003.spamdul.parking.entities.pass.car;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarParkingPassTest {

  public static final CarParkingPassCode CAR_PARKING_PASS_CODE = CarParkingPassCode.valueOf("1234");
  public static final ParkingZone CAR_PARKING_ZONE = ParkingZone.ZONE_1;
  public static final TimePeriod TIME_PERIOD = new TimePeriod(LocalDateTime.MIN,
                                                              LocalDateTime.MAX,
                                                              TimePeriodDayOfWeek.ALL);
  private CarParkingPass carParkingPass;

  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    carParkingPass = new CarParkingPass(CAR_PARKING_PASS_CODE, CAR_PARKING_ZONE, TIME_PERIOD);
  }

  @Test
  public void whenAccepting_shouldAssociateToUserCarParkingPass() {
    carParkingPass.accept(user);

    verify(user, times(1)).associateCarParkingPass(carParkingPass);
  }
}
