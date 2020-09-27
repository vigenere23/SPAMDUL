package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.calendar.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassFactoryTest {

  private final UserId A_USER_ID = new UserId();
  private final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private final PassType A_PASS_TYPE = PassType.MONTHLY;

  private PassFactory passFactory;

  @Before
  public void setUpCalendar() {

    passFactory = new PassFactory();
  }

  @Test
  public void whenCreatingPass_shouldCreatePassWithRightInfo() {
    Pass pass = passFactory.create(A_USER_ID, A_PARKING_ZONE, A_PASS_TYPE);

    assertThat(pass.getUserId()).isEqualTo(A_USER_ID);
    assertThat(pass.getParkingZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(pass.getPassType()).isEqualTo(A_PASS_TYPE);
  }
}
