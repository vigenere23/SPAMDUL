package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.Calendar;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriod;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimePeriodBoundaryValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final LocalDateTime A_LOCAL_DATE_TIME = LocalDateTime.of(1000, 1, 1, 0, 0);

  private Calendar calendar = mock(Calendar.class);
  private CarParkingPassCode parkingPassCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);

  private TimePeriodBoundaryValidatorCarParking timePeriodBoundaryValidator = new TimePeriodBoundaryValidatorCarParking(calendar);
  @Mock
  private TimePeriod timePeriod;
  @Mock
  private UserRepository userRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private CarParkingPass parkingPass;
  @Mock
  private User user;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    CarParkingPassValidator.setPassRepository(userRepository);
    when(user.getCarParkingPass()).thenReturn(parkingPass);
  }

  @After
  public void clearStatic() {
    CarParkingPassValidator.setPassRepository(null);
    CarParkingPassValidator.passCache.clear();
  }

  @Test
  public void whenValidate_shouldGetCorrespondingPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(userRepository).findBy(parkingPassCode);
  }

  @Test
  public void whenValidate_shouldGetTimePeriodFromPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(parkingPass).getTimePeriod();
  }

  @Test
  public void whenValidate_shouldCallCalendarNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(calendar).now();
  }

  @Test
  public void whenValidate_shouldChekIfTimePeriodBoundsNow() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(timePeriod).bounds(A_LOCAL_DATE_TIME);
  }

  @Test
  public void givenNotBoundingTimePeriod_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(false);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("VIG_02");

    timePeriodBoundaryValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    timePeriodBoundaryValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.getTimePeriod()).thenReturn(timePeriod);
    when(calendar.now()).thenReturn(A_LOCAL_DATE_TIME);
    when(timePeriod.bounds(A_LOCAL_DATE_TIME)).thenReturn(true);

    timePeriodBoundaryValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
