package ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingAccessValidator;
import ca.ulaval.glo4003.spamdul.entity.user.User;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BikeParkingPassServiceTest {

  public static final BikeParkingPassCode BIKE_PARKING_ACCESS_CODE = BikeParkingPassCode.valueOf("1234");
  private BikeParkingAccessService bikeParkingAccessService;

  @Mock
  private UserRepository userRepository;
  @Mock
  private BikeParkingAccessValidator bikeParkingAccessValidator;
  @Mock
  private User user;

  @Before
  public void setUp() throws Exception {
    bikeParkingAccessService = new BikeParkingAccessService(userRepository, bikeParkingAccessValidator);
  }

  @Test
  public void whenVerifyingIfCanAccessBikeParking_shouldFindUserInRepository() {
    when(userRepository.findBy(BIKE_PARKING_ACCESS_CODE)).thenReturn(user);

    bikeParkingAccessService.canAccessParking(BIKE_PARKING_ACCESS_CODE);

    verify(userRepository, times(1)).findBy(BIKE_PARKING_ACCESS_CODE);
  }

  @Test
  public void givenNoUserWithBikeParkingAccess_whenVerifyingIfCanAccessBikeParking_shouldReturnFalse() {
    when(userRepository.findBy(BIKE_PARKING_ACCESS_CODE)).thenThrow(UserNotFoundException.class);

    boolean isAccessGranted = bikeParkingAccessService.canAccessParking(BIKE_PARKING_ACCESS_CODE);

    assertThat(isAccessGranted).isFalse();
  }

  @Test
  public void whenVerifyingIfCanAccessBikeParking_shouldCallUserToVerifyIfCanAccessBikeParking() {
    when(userRepository.findBy(BIKE_PARKING_ACCESS_CODE)).thenReturn(user);

    bikeParkingAccessService.canAccessParking(BIKE_PARKING_ACCESS_CODE);

    verify(user, times(1)).isAccessGrantedToBikeParking(bikeParkingAccessValidator);
  }

  @Test
  public void givenUserGrantAccessToBikeParking_whenVerifyingIfCanAccessBikeParking_shouldGrantAccess() {
    when(userRepository.findBy(BIKE_PARKING_ACCESS_CODE)).thenReturn(user);
    when(user.isAccessGrantedToBikeParking(bikeParkingAccessValidator)).thenReturn(true);

    boolean isAccessGranted = bikeParkingAccessService.canAccessParking(BIKE_PARKING_ACCESS_CODE);

    assertThat(isAccessGranted).isTrue();
  }

  @Test
  public void givenUserNotGrantingAccessToBikeParking_whenVerifyingIfCanAccessBikeParking_shouldNotGrantAccess() {
    when(userRepository.findBy(BIKE_PARKING_ACCESS_CODE)).thenReturn(user);
    when(user.isAccessGrantedToBikeParking(bikeParkingAccessValidator)).thenReturn(false);

    boolean isAccessGranted = bikeParkingAccessService.canAccessParking(BIKE_PARKING_ACCESS_CODE);

    assertThat(isAccessGranted).isFalse();
  }
}