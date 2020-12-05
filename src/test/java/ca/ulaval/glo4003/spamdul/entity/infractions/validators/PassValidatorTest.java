package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.UserRepositoryNotSetException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PassValidatorTest extends PassValidator {

  public static final PassCode A_PASS_CODE = new PassCode();
  public static final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;
  public static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  @Mock
  private UserRepository userRepository;
  @Mock
  User user;
  private Pass pass;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
  }

  @Before
  public void setUp() {
    TimePeriod timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_TIME_PERIOD_DAY_OF_WEEK);
    pass = new Pass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);
    when(user.getPass()).thenReturn(pass);
  }

  @After
  public void clearStatic() {
    PassValidator.setPassRepository(null);
    PassValidator.userCache.clear();
  }

  @Test
  public void givenNextValidator_whenNextValidation_shouldCallValidateOnNext() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    setNextValidator(nextPassValidator);

    nextValidation(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }

  @Test
  public void givenNoNextValidator_whenNextValidation_shouldRemoveFromCacheCorrespondingPass() {
    passToValidateDto.passCode = A_PASS_CODE.toString();
    userCache.put(A_PASS_CODE, pass);

    nextValidation(passToValidateDto);

    assertThat(userCache).doesNotContainKey(A_PASS_CODE);
  }

  @Test
  public void givenCacheHit_whenGetCorrespondingPass_shouldReturnRightPass() {
    userCache.put(A_PASS_CODE, pass);

    Pass actual = getCorrespondingUser(A_PASS_CODE);

    assertThat(actual).isEqualTo(pass);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldFindPassInRepo() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    PassValidator.setPassRepository(userRepository);

    getCorrespondingUser(A_PASS_CODE);

    verify(userRepository).findBy(A_PASS_CODE);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldAddToCache() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    PassValidator.setPassRepository(userRepository);

    getCorrespondingUser(A_PASS_CODE);

    assertThat(userCache).containsEntry(A_PASS_CODE, pass);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldReturnRightPass() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    PassValidator.setPassRepository(userRepository);

    Pass actual = getCorrespondingUser(A_PASS_CODE);

    assertThat(actual).isEqualTo(pass);
  }

  @Test(expected = UserRepositoryNotSetException.class)
  public void givenRepoNotSetAndCacheMiss_whenGetCorrespondingPass_shouldThrowRepoNotSetException() {
    getCorrespondingUser(A_PASS_CODE);
  }
}
