package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.sale.PassSaleDto;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaleResourceImplTest {

  private PassSaleRequest A_PASS_SALE_REQUEST = new PassSaleRequest();
  private PassSaleDto A_PASS_SALE_DTO = new PassSaleDto();
  private SaleService saleService;
  private PassSaleAssembler passSaleAssembler;
  private SaleResource saleResourceImpl;

  @Before
  public void setUp() {
    saleService = mock(SaleService.class);
    passSaleAssembler = mock(PassSaleAssembler.class);

    saleResourceImpl = new SaleResourceImpl(saleService, passSaleAssembler);
    given(passSaleAssembler.fromDto(A_PASS_SALE_REQUEST)).willReturn(A_PASS_SALE_DTO);
  }

  @Test
  public void whenwhenSellingPasse_thenShouldCallSaleAssembler() {
    saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);

    verify(passSaleAssembler).fromDto(A_PASS_SALE_REQUEST);
  }

  @Test
  public void whenwhenSellingPass_thenShouldCallSaleServiceToCreateSale() {
    saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);

    verify(saleService).createSale(A_PASS_SALE_DTO);
  }

  @Test
  public void whenSellingPass_responseCodeShouldBe201() {
    Response response = saleResourceImpl.sellPass(A_PASS_SALE_REQUEST);
    assertThat(response.getStatus()).isEqualTo(201);
  }

}
