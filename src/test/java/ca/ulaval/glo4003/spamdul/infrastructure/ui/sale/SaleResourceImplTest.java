package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassDto;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SaleResourceImplTest {

  private PassSaleRequest A_PASS_SALE_REQUEST = new PassSaleRequest();
  private PassDto A_PASS_SALE_DTO = new PassDto();
  @Mock
  private PassService passService;
  @Mock
  private PassSaleAssembler passSaleAssembler;
  private SaleResource saleResourceImpl;

  @Before
  public void setUp() {
    saleResourceImpl = new SaleResourceImpl(passService, passSaleAssembler);
    when(passSaleAssembler.fromRequest(A_PASS_SALE_REQUEST)).thenReturn(A_PASS_SALE_DTO);
  }

  @Test
  public void whenSellingPasse_thenShouldCallSaleAssembler() {
    saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);

    verify(passSaleAssembler).fromRequest(A_PASS_SALE_REQUEST);
  }

  @Test
  public void whenSellingPass_thenShouldCallSaleServiceToCreateSale() {
    saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);

    verify(passService).createPass(A_PASS_SALE_DTO);
  }

  @Test
  public void whenSellingPass_responseCodeShouldBe201() {
    Response response = saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);
    assertThat(response.getStatus()).isEqualTo(201);
  }

}
