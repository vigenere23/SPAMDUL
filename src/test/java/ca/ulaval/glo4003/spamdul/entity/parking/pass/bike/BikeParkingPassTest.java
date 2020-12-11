package ca.ulaval.glo4003.spamdul.entity.parking.pass.bike;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BikeParkingPassTest {

  public static final TimePeriod TIME_PERIOD = new TimePeriod(LocalDateTime.MIN,
                                                              LocalDateTime.MAX,
                                                              TimePeriodDayOfWeek.ALL);
  public static final BikeParkingPassCode BIKE_PARKING_PASS_CODE = BikeParkingPassCode.valueOf("1234");

  private BikeParkingPass bikeParkingPass;

  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    bikeParkingPass = new BikeParkingPass(BIKE_PARKING_PASS_CODE, TIME_PERIOD);
  }

  @Test
  public void whenAccepting_shouldAssociateToUserBikeParkingPass() {
    bikeParkingPass.accept(user);

    verify(user, times(1)).associateBikeParkingPass(bikeParkingPass);
  }
}