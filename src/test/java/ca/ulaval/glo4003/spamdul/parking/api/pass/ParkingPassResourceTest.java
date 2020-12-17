package ca.ulaval.glo4003.spamdul.parking.api.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.parking.api.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.usecases.pass.ParkingPassUseCase;
import ca.ulaval.glo4003.spamdul.parking.usecases.pass.PassDto;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingPassResourceTest {

  private final PassCreationRequest A_PASS_CREATION_REQUEST = new PassCreationRequest();
  private final PassDto A_PASS_DTO = new PassDto();
  @Mock
  private ParkingPassUseCase parkingPassUseCase;
  @Mock
  private PassAssembler passAssembler;
  private ParkingPassResource parkingPassResource;

  @Before
  public void setUp() {
    parkingPassResource = new ParkingPassResource(parkingPassUseCase, passAssembler);
    when(passAssembler.fromRequest(A_PASS_CREATION_REQUEST)).thenReturn(A_PASS_DTO);
  }

  @Test
  public void whenSellingPasse_thenShouldCallPassAssembler() {
    parkingPassResource.sellPass(A_PASS_CREATION_REQUEST);

    verify(passAssembler).fromRequest(A_PASS_CREATION_REQUEST);
  }

  @Test
  public void whenSellingPass_thenShouldCallPassServiceToCreatePass() {
    parkingPassResource.sellPass(A_PASS_CREATION_REQUEST);

    verify(parkingPassUseCase).createPass(A_PASS_DTO);
  }

  @Test
  public void whenSellingPass_responseCodeShouldBe201() {
    Response response = parkingPassResource.sellPass(A_PASS_CREATION_REQUEST);

    assertThat(response.getStatus()).isEqualTo(201);
  }

}
