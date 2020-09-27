package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.campusaccessexceptions.InvalidPeriodArgumentException;
import java.time.DayOfWeek;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessFactoryTest {

  private final UserId A_USER_ID = new UserId();
  private final CarId A_CAR_ID = new CarId();
  private final Period A_PERIOD = Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER;
  private final DayOfWeek A_CAMPUS_ACCESS_DAY = DayOfWeek.WEDNESDAY;

  private CampusAccessFactory campusAccessFactory = new CampusAccessFactory();

  @Before
  public void setUp() throws Exception {
    campusAccessFactory = new CampusAccessFactory();
  }

  @Test
  public void whenCreatingCampusAccess_shouldCreateCamusAccess() {
    CampusAccess campusAccess = campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, A_CAMPUS_ACCESS_DAY);

    assertThat(campusAccess.getUserId()).isEqualTo(A_USER_ID);
    assertThat(campusAccess.getCarId()).isEqualTo(A_CAR_ID);
    assertThat(campusAccess.getPeriod()).isEqualTo(A_PERIOD);
    assertThat(campusAccess.getDayOfWeek()).isEqualTo(A_CAMPUS_ACCESS_DAY);
    assertThat(campusAccess.getCampusAccessCode()).isNotNull();
  }

  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSaturdayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, DayOfWeek.SATURDAY);
  }

  @Test(expected = InvalidDayToAccessCampusException.class)
  public void givenSundayAsAccessCampusDay_whenCreatingCampusAccess_shouldThrowInvalidCampusAccessDayException() {
    campusAccessFactory.create(A_USER_ID, A_CAR_ID, A_PERIOD, DayOfWeek.SUNDAY);
  }

  @Test(expected = InvalidPeriodArgumentException.class)
  public void givenAnInvalidPeriod_whenCreatingCampusAccess_shouldThrowInvalidPeriodException() {
    campusAccessFactory.create(A_USER_ID, A_CAR_ID, Period.SEMESTER_1, A_CAMPUS_ACCESS_DAY);
  }
}