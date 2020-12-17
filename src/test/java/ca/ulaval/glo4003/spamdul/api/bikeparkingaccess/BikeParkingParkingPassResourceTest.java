package ca.ulaval.glo4003.spamdul.api.bikeparkingaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.api.bikeparkingaccess.dto.BikeParkingAccessResponse;
import ca.ulaval.glo4003.spamdul.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.entity.parking.pass.bike.BikeParkingPassCode;
import ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess.BikeParkingAccessUseCase;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BikeParkingParkingPassResourceTest {

  public static final String BIKE_PARKING_ACCESS_CODE_STRING = "12345";
  private BikeParkingAccessResource bikeParkingAccessResource;
  private BikeParkingAccessAssembler bikeParkingAccessAssembler;

  @Mock
  BikeParkingAccessUseCase bikeParkingAccessUseCase;

  @Before
  public void setUp() throws Exception {
    bikeParkingAccessAssembler = new BikeParkingAccessAssembler();
    bikeParkingAccessResource = new BikeParkingAccessResource(bikeParkingAccessUseCase, bikeParkingAccessAssembler);
  }

  @Test
  public void whenTryingToAccessBikeParking_shouldCallServiceWithRightBikeParkingAccessCode() {
    bikeParkingAccessResource.accessBikeParking(BIKE_PARKING_ACCESS_CODE_STRING);

    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(BIKE_PARKING_ACCESS_CODE_STRING);
    verify(bikeParkingAccessUseCase, times(1)).canAccessParking(bikeParkingPassCode);
  }

  @Test
  public void givenAccessGranted_whenTryingToAccessBikeParking_shouldSetResponseToAccessGranted() {
    BikeParkingPassCode bikeParkingPassCode = BikeParkingPassCode.valueOf(BIKE_PARKING_ACCESS_CODE_STRING);
    when(bikeParkingAccessUseCase.canAccessParking(bikeParkingPassCode)).thenReturn(true);

    Response response = bikeParkingAccessResource.accessBikeParking(BIKE_PARKING_ACCESS_CODE_STRING);

    BikeParkingAccessResponse bikeParkingAccessResponse = (BikeParkingAccessResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
    assertThat(bikeParkingAccessResponse.access).isEqualTo("GRANTED");
  }
}
