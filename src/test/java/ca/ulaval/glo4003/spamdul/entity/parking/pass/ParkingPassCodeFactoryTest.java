package ca.ulaval.glo4003.spamdul.entity.parking.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.ids.IdGenerator;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.car.CarParkingPassCode;
import ca.ulaval.glo4003.spamdul.entity.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingPassCodeFactoryTest {

  private static final String A_VALUE = "123";
  public static final ParkingZone A_CAR_PARKING_ZONE = ParkingZone.ZONE_1;

  @Mock
  private IdGenerator idGenerator;
  @Mock
  private UserRepository bikeParkingPassCodeRepository;


  private ParkingPassCodeFactory parkingPassCodeFactory;

  @Before
  public void setUp() {
    parkingPassCodeFactory = new ParkingPassCodeFactory(idGenerator);
  }

  @Test
  public void whenCreating_shouldReturnFromIdGenerator() {
    when(idGenerator.generate()).thenReturn(A_VALUE);

    ParkingPassCode parkingPassCode = parkingPassCodeFactory.create(A_CAR_PARKING_ZONE);

    assertThat(parkingPassCode.toString()).isEqualTo(A_VALUE);
  }

  @Test
  public void givenABikeParkingZone_whenCreatingPassCode_shouldReturnBikeParkingAccessCode() {
    ParkingPassCode parkingPassCode = parkingPassCodeFactory.create(ParkingZone.ZONE_BIKE);

    assertThat(parkingPassCode.getClass()).isEqualTo(BikeParkingPassCode.class);
  }

  @Test
  public void givenAnyOtherParkingZone_whenCreatingPassCode_shouldReturnCarParkingPassCode() {
    ParkingPassCode parkingPassCode = parkingPassCodeFactory.create(A_CAR_PARKING_ZONE);

    assertThat(parkingPassCode.getClass()).isEqualTo(CarParkingPassCode.class);
  }
}
