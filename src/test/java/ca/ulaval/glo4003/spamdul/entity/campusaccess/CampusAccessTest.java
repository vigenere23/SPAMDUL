package ca.ulaval.glo4003.spamdul.entity.campusaccess;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.spamdul.entity.car.CarId;
import ca.ulaval.glo4003.spamdul.entity.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.pass.exceptions.PassNotAcceptedByAccessException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class CampusAccessTest {

  public static final PassCode A_PASS_CODE = new PassCode();
  private final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
  private final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2020, 2, 1, 0, 0);
  private final LocalDateTime A_WEDNESDAY_IN_THE_MIDDLE = LocalDateTime.of(2020, 1, 15, 0, 0);
  private final LocalDateTime A_DATE_TIME_BEFORE = LocalDateTime.of(2019, 1, 1, 0, 0);

  private TimePeriod timePeriod;
  private CampusAccess campusAccess;

  @Before
  public void setUp() throws Exception {
    timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, TimePeriodDayOfWeek.WEDNESDAY);
    campusAccess = new CampusAccess(new CampusAccessCode(),
                                    new UserId(),
                                    new CarId(),
                                    timePeriod);
  }

  @Test
  public void givenDateTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldGrantAccess() {
    boolean grantedAccess = campusAccess.isAccessGranted(A_WEDNESDAY_IN_THE_MIDDLE);

    assertThat(grantedAccess).isTrue();
  }

  @Test
  public void givenDateNotTimeIncludedInPeriod_whenVerifyingIfGrantedAccess_shouldNotGrantAccess() {

    boolean grantedAccess = campusAccess.isAccessGranted(A_DATE_TIME_BEFORE);

    assertThat(grantedAccess).isFalse();
  }

  @Test
  public void givenPassPeriodIncluded_whenAssociatingSingleDayPerWeekPassOnSameDay_shouldSetPassCode() {
    final TimePeriod INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                           A_END_DATE_TIME,
                                                           TimePeriodDayOfWeek.WEDNESDAY);

    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);

    assertThat(campusAccess.getAssociatedPassCode()).isEqualTo(A_PASS_CODE);
  }

  @Test(expected = PassAlreadyAssociatedException.class)
  public void givenPassAlreadyAssociated_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                           A_END_DATE_TIME,
                                                           TimePeriodDayOfWeek.WEDNESDAY);
    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);

    campusAccess.associatePass(A_PASS_CODE, INCLUDED_TIME_PERIOD);
  }

  @Test(expected = PassNotAcceptedByAccessException.class)
  public void givenPassPeriodNotIncluded_whenAssociatingSingleDayPerWeekPassOnOtherDay_shouldThrow() {
    final TimePeriod NOT_INCLUDED_TIME_PERIOD = new TimePeriod(A_START_DATE_TIME,
                                                               A_END_DATE_TIME,
                                                               TimePeriodDayOfWeek.FRIDAY);

    campusAccess.associatePass(A_PASS_CODE, NOT_INCLUDED_TIME_PERIOD);
  }
}