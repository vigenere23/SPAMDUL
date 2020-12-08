package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.UserFinderService;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.WrongZoneInfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParkingZoneValidatorTest {

  public static final String A_VALID_PASS_CODE_STRING = "9";
  public static final ParkingZone A_PARKING_ZONE = ParkingZone.ZONE_2;

  private ParkingZoneValidator parkingZoneValidator;
  @Mock
  private UserFinderService userFinderService;
  private PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private User user;

  @Before
  public void setUp() {
    parkingZoneValidator = new ParkingZoneValidator(userFinderService);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    passToValidateDto.parkingZone = A_PARKING_ZONE;
  }


  @Test
  public void whenValidate_shouldFindUser() {
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(userFinderService).findBy(passCode);
  }

  @Test
  public void whenValidate_shouldTellUserToValidateParkingZone() {
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(user).canParkInZone(passToValidateDto.parkingZone);
  }

  @Test(expected = WrongZoneInfractionException.class)
  public void givenInvalidParkingZone_whenValidate_shouldThrowInfractionException() {
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(false);

    parkingZoneValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    parkingZoneValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userFinderService.findBy(passCode)).thenReturn(user);
    when(user.canParkInZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
