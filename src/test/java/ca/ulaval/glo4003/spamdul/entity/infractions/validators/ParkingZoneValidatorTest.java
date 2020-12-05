package ca.ulaval.glo4003.spamdul.entity.infractions.validators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.entity.infractions.exceptions.InfractionException;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.Pass;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.PassCode;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
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

  private final ParkingZoneValidator parkingZoneValidator = new ParkingZoneValidator();
  @Mock
  private UserRepository userRepository;
  private final PassToValidateDto passToValidateDto = new PassToValidateDto();
  @Mock
  private User user;
  @Mock
  private Pass pass;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Before
  public void setUp() {
    PassValidator.setPassRepository(userRepository);
    passToValidateDto.parkingZone = A_PARKING_ZONE;
    when(user.getPass()).thenReturn(pass);
  }

  @After
  public void clearStatic() {
    PassValidator.setPassRepository(null);
    PassValidator.passCache.clear();
  }

  @Test
  public void whenValidate_shouldGetCorrespondingPass() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(userRepository).findBy(passCode);
  }

  @Test
  public void whenValidate_shouldTellPassToValidateParkingZone() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(pass).isAValidParkingZone(passToValidateDto.parkingZone);
  }

  @Test
  public void givenInvalidParkingZone_whenValidate_shouldThrowInfractionException() {
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(false);

    exceptionRule.expect(InfractionException.class);
    exceptionRule.expectMessage("ZONE_01");

    parkingZoneValidator.validate(passToValidateDto);
  }

  @Test
  public void givenValidParkingZone_whenValidate_shouldCallNextValidation() {
    PassValidator nextPassValidator = mock(PassValidator.class);
    parkingZoneValidator.setNextValidator(nextPassValidator);
    passToValidateDto.passCode = A_VALID_PASS_CODE_STRING;
    PassCode passCode = PassCode.valueOf(A_VALID_PASS_CODE_STRING);
    when(userRepository.findBy(passCode)).thenReturn(user);
    when(pass.isAValidParkingZone(A_PARKING_ZONE)).thenReturn(true);

    parkingZoneValidator.validate(passToValidateDto);

    verify(nextPassValidator).validate(passToValidateDto);
  }
}
