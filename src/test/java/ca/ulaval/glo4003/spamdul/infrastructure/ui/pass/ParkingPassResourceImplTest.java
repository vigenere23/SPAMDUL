package ca.ulaval.glo4003.spamdul.infrastructure.ui.pass;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.PassDto;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.ParkingPassService;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingPassResourceImplTest {

  private final PassCreationRequest A_PASS_CREATION_REQUEST = new PassCreationRequest();
  private final PassDto A_PASS_DTO = new PassDto();
  @Mock
  private ParkingPassService parkingPassService;
  @Mock
  private PassAssembler passAssembler;
  private PassResource passResourceImpl;

  @Before
  public void setUp() {
    passResourceImpl = new PassResourceImpl(parkingPassService, passAssembler);
    when(passAssembler.fromRequest(A_PASS_CREATION_REQUEST)).thenReturn(A_PASS_DTO);
  }

  @Test
  public void whenSellingPasse_thenShouldCallPassAssembler() {
    passResourceImpl.sellPass(A_PASS_CREATION_REQUEST);

    verify(passAssembler).fromRequest(A_PASS_CREATION_REQUEST);
  }

  @Test
  public void whenSellingPass_thenShouldCallPassServiceToCreatePass() {
    passResourceImpl.sellPass(A_PASS_CREATION_REQUEST);

    verify(parkingPassService).createPass(A_PASS_DTO);
  }

  @Test
  public void whenSellingPass_responseCodeShouldBe201() {
    Response response = passResourceImpl.sellPass(A_PASS_CREATION_REQUEST);
    assertThat(response.getStatus()).isEqualTo(201);
  }

}