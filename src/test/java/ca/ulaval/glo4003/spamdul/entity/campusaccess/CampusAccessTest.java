package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.PassSaleNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.DayOfWeek;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessTest {

  public static final PassCode A_PASS_CODE = new PassCode();
  private final DayOfWeek A_DAY_OF_THE_WEEK = DayOfWeek.MONDAY;
  private final DayOfWeek ANOTHER_DAY_OF_THE_WEEK = DayOfWeek.SATURDAY;

  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                                 new UserId(),
                                                 new CarId(),
                                                 A_DAY_OF_THE_WEEK,
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

  //TODO: remove those ugly test when refactoring is done
  @Test
  public void givenSingleDayPerWeekPeriod_whenAssociatingSingleDayPerWeekPassOnSameDay_shouldSetPassCode() {
    campusAccess.associatePass(A_PASS_CODE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, A_DAY_OF_THE_WEEK);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }

  @Test(expected = PassAlreadyAssociatedException.class)
  public void givenPassAlreadyAssociated_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    campusAccess.setAssociatedPassCode(new PassCode());

    campusAccess.associatePass(A_PASS_CODE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, ANOTHER_DAY_OF_THE_WEEK);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }

  @Test(expected = PassSaleNotAcceptedByAccessException.class)
  public void givenSingleDayPerWeekPeriod_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    campusAccess.associatePass(A_PASS_CODE, PassType.SINGLE_DAY_PER_WEEK_PER_SEMESTER, ANOTHER_DAY_OF_THE_WEEK);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }

  @Test(expected = PassSaleNotAcceptedByAccessException.class)
  public void givenSingleDayPerWeekPeriod_whenAssociatingOtherTypeOfPass_shouldThrow() {
    campusAccess.associatePass(A_PASS_CODE, PassType.MONTHLY, ANOTHER_DAY_OF_THE_WEEK);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }
}