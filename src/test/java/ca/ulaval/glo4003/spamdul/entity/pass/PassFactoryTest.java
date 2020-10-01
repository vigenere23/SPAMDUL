package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.exceptions.InvalidPassDayOfWeekException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.DayOfWeek;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PassFactoryTest {

  private static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;
  private static final DayOfWeek A_DAY_OF_WEEK = DayOfWeek.MONDAY;

  private PassFactory passFactory;

  @Before
  public void setUp() {
    passFactory = new PassFactory();
  }

  @Test
  public void givenSingleDayPerWeekType_whenCreatingPass_shouldCreatePassWithRightInfo() {
    Pass pass = passFactory.create(A_PARKING_ZONE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, A_DAY_OF_WEEK);

    assertThat(pass.getParkingZone()).isEqualTo(A_PARKING_ZONE);
    assertThat(pass.getPassType()).isEqualTo(PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
    assertThat(pass.getDayOfWeek()).isEqualTo(A_DAY_OF_WEEK);
    assertThat(pass.getPassCode()).isNotNull();
  }

  @Test(expected = InvalidPassDayOfWeekException.class)
  public void givenSaturdayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    passFactory.create(A_PARKING_ZONE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, DayOfWeek.SATURDAY);
  }

  @Test(expected = InvalidPassDayOfWeekException.class)
  public void givenSundayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    passFactory.create(A_PARKING_ZONE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, DayOfWeek.SUNDAY);
  }

  @Test(expected = InvalidPassArgumentException.class)
  public void givenOtherType_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    passFactory.create(A_PARKING_ZONE, PassType.MONTHLY, DayOfWeek.MONDAY);
  }
}
