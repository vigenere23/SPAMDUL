package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingPass;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingZoneValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  private ParkingZoneValidatorCarParking parkingZoneValidator = new ParkingZoneValidatorCarParking();
  private CarParkingPassCode parkingPassCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);
  @Mock
  private UserRepository userRepository;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private User user;
  @Mock
  private CarParkingPass parkingPass;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    CarParkingPassValidator.setPassRepository(userRepository);
    passToValidateDto.parkingZone = A_PARKING_ZONE;
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
    when(parkingPass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(userRepository).findBy(parkingPassCode);
  }

  @Test
  public void whenValidate_shouldTellPassToValidateParkingZone() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(parkingPass).isAValidParkingZone(passToValidateDto.parkingZone);
  }

  @Test
  public void givenInvalidParkingZone_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(false);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("ZONE_01");

    parkingZoneValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    parkingZoneValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userRepository.findBy(parkingPassCode)).thenReturn(user);
    when(parkingPass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
