package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.PassRepositoryNotSetException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDayOfWeek;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingCarParkingPassValidatorTest extends CarParkingPassValidator {

  public static final CarParkingPassCode A_PASS_CODE = CarParkingPassCode.valueOf("123");
  public static final TimePeriodDayOfWeek A_TIME_PERIOD_DAY_OF_WEEK = TimePeriodDayOfWeek.FRIDAY;
  public static final LocalDateTime A_END_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final LocalDateTime A_START_DATE_TIME = LocalDateTime.of(2000, 1, 1, 1, 1);
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  @Mock
  private UserRepository userRepository;
  @Mock
  User user;
  private CarParkingPass parkingPass;
  private final PassToValidateDto passToValidateDto = new PassToValidateDto();


  @Override
  public void validate(PassToValidateDto passToValidateDto) {
  }

  @Before
  public void setUp() {
    TimePeriod timePeriod = new TimePeriod(A_START_DATE_TIME, A_END_DATE_TIME, A_TIME_PERIOD_DAY_OF_WEEK);
    parkingPass = new CarParkingPass(A_PASS_CODE, A_PARKING_ZONE, timePeriod);
    when(user.getCarParkingPass()).thenReturn(parkingPass);
  }

  @After
  public void clearStatic() {
    CarParkingPassValidator.setPassRepository(null);
    CarParkingPassValidator.passCache.clear();
  }

  @Test
  public void givenNextValidator_whenNextValidation_shouldCallValidateOnNext() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    setNextValidator(nextCarParkingPassValidator);

    nextValidation(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }

  @Test
  public void givenNoNextValidator_whenNextValidation_shouldRemoveFromCacheCorrespondingPass() {
    passToValidateDto.passCode = A_PASS_CODE.toString();
    passCache.put(A_PASS_CODE, parkingPass);

    nextValidation(passToValidateDto);

    assertThat(passCache).doesNotContainKey(A_PASS_CODE);
  }

  @Test
  public void givenCacheHit_whenGetCorrespondingPass_shouldReturnRightPass() {
    passCache.put(A_PASS_CODE, parkingPass);

    ParkingPass actual = getCorrespondingPass(A_PASS_CODE);

    assertThat(actual).isEqualTo(parkingPass);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldFindPassInRepo() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    CarParkingPassValidator.setPassRepository(userRepository);

    getCorrespondingPass(A_PASS_CODE);

    verify(userRepository).findBy(A_PASS_CODE);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldAddToCache() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    CarParkingPassValidator.setPassRepository(userRepository);

    getCorrespondingPass(A_PASS_CODE);

    assertThat(passCache).containsEntry(A_PASS_CODE, parkingPass);
  }

  @Test
  public void givenRepoSetAndCacheMiss_whenGetCorrespondingPass_shouldReturnRightPass() {
    when(userRepository.findBy(A_PASS_CODE)).thenReturn(user);
    CarParkingPassValidator.setPassRepository(userRepository);

    ParkingPass actual = getCorrespondingPass(A_PASS_CODE);

    assertThat(actual).isEqualTo(parkingPass);
  }

  @Test(expected = PassRepositoryNotSetException.class)
  public void givenRepoNotSetAndCacheMiss_whenGetCorrespondingPass_shouldThrowRepoNotSetException() {
    getCorrespondingPass(A_PASS_CODE);
  }
}
