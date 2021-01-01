package ca.ulaval.glo4003.spamdul.parking.entities.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.parking.entities.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.parking.entities.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingZoneValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  private CarParkingZoneValidator parkingZoneValidator;
  private CarParkingPassCode passCode = CarParkingPassCode.valueOf(A_VALID_PASS_CODE_STRING);
  private PassToValidateDto passToValidateDto = new PassToValidateDto();

  @Mock
  private UserFinderService userFinderService;
  @Mock
  private User user;

  @Before
  public void setUp() {
    parkingZoneValidator = new CarParkingZoneValidator(userFinderService);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    passToValidateDto.parkingZone = A_PARKING_ZONE;
  }

  @Test
  public void whenValidate_shouldFindUser() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(userFinderService).findBy(passCode);
  }

  @Test
  public void whenValidate_shouldTellUserToValidateParkingZone() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(user).canParkInZone(passToValidateDto.parkingZone);
  }

  @Test(expected = WrongZoneInfractionException.class)
  public void givenInvalidParkingZone_whenValidate_shouldThrowInfractionException() {
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(false);

    parkingZoneValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    CarParkingPassValidator nextCarParkingPassValidator = mock(CarParkingPassValidator.class);
    parkingZoneValidator.setNextValidator(nextCarParkingPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(nextCarParkingPassValidator).validate(passToValidateDto);
  }
}
