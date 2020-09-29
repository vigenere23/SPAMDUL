package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.DayOfWeek;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessTest {

  private final DayOfWeek A_DAY_OF_THE_WEEK = DayOfWeek.MONDAY;
  private final DayOfWeek ANOTHER_DAY_OF_THE_WEEK = DayOfWeek.SATURDAY;

  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                                 new UserId(),
                                                 new CarId(),
                                                 DayOfWeek.MONDAY,
                                                 Period.SINGLE_DAY_PER_WEEK_PER_SEMESTER);
  }

  @Test
  public void givenTheSameDayOfTheWeek_whenVerifyingIfGrantedAccess_shouldGrantAccess() {
    boolean grantedAccess = campusAccess.isAccessGranted(A_DAY_OF_THE_WEEK);

    assertThat(grantedAccess).isTrue();
  }

  @Test
  public void givenAnotherDayOfTheWeek_whenVerifyingIfGrantedAccess_shouldNotGrantAccess() {
    boolean grantedAccess = campusAccess.isAccessGranted(ANOTHER_DAY_OF_THE_WEEK);

    assertThat(grantedAccess).isFalse();
  }
}