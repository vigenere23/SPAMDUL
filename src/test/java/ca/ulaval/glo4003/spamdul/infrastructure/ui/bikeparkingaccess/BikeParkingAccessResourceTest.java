package ca.ulaval.glo4003.spamdul.infrastructure.ui.bikeparkingaccess;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.parking.bikeparkingpaccess.BikeParkingAccessCode;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.bikeparkingaccess.dto.BikeParkingAccessResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.bikeparking.BikeParkingAccessAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.bikeparkingaccess.BikeParkingAccessService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BikeParkingAccessResourceTest {

  public static final String BIKE_PARKING_ACCESS_CODE_STRING = "12345";
  private BikeParkingAccessResource bikeParkingAccessResource;
  private BikeParkingAccessAssembler bikeParkingAccessAssembler;

  @Mock
  BikeParkingAccessService bikeParkingAccessService;

  @Before
  public void setUp() throws Exception {
    bikeParkingAccessAssembler = new BikeParkingAccessAssembler();
    bikeParkingAccessResource = new BikeParkingAccessResource(bikeParkingAccessService, bikeParkingAccessAssembler);
  }

  @Test
  public void whenTryingToAccessBikeParking_shouldCallServiceWithRightBikeParkingAccessCode() {
    bikeParkingAccessResource.accessBikeParking(BIKE_PARKING_ACCESS_CODE_STRING);

    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(BIKE_PARKING_ACCESS_CODE_STRING);
    verify(bikeParkingAccessService, times(1)).canAccessParking(bikeParkingAccessCode);
  }

  @Test
  public void givenAccessGranted_whenTryingToAccessBikeParking_shouldSetResponseToAccessGranted() {
    BikeParkingAccessCode bikeParkingAccessCode = BikeParkingAccessCode.valueOf(BIKE_PARKING_ACCESS_CODE_STRING);
    when(bikeParkingAccessService.canAccessParking(bikeParkingAccessCode)).thenReturn(true);

    Response response = bikeParkingAccessResource.accessBikeParking(BIKE_PARKING_ACCESS_CODE_STRING);

    BikeParkingAccessResponse bikeParkingAccessResponse = (BikeParkingAccessResponse) response.getEntity();
    assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
    assertThat(bikeParkingAccessResponse.access).isEqualTo("GRANTED");
  }
}