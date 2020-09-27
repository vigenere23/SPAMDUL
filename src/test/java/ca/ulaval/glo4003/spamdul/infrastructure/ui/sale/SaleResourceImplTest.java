package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.SaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.SaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleDto;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleService;
import org.junit.Before;
import org.junit.Test;

public class SaleResourceImplTest {

  private SaleRequest A_SALE_REQUEST = new SaleRequest();
  private SaleDto A_SALE_DTO = new SaleDto();
  private SaleService saleService;
  private SaleAssembler saleAssembler;
  private SaleResource saleResourceImpl;

  @Before
  public void setUp() {
    saleService = mock(SaleService.class);
    saleAssembler = mock(SaleAssembler.class);

    saleResourceImpl = new SaleResourceImpl(saleService, saleAssembler);
    given(saleAssembler.fromDto(A_SALE_REQUEST)).willReturn(A_SALE_DTO);
  }

  @Test
  public void whenCreatingSale_thenShouldCallSaleAssembler() {
    saleResourceImpl.createSale(A_SALE_REQUEST);

    verify(saleAssembler).fromDto(A_SALE_REQUEST);
  }

  @Test
  public void whenCreatingSale_thenShouldCallSaleServiceToCreateSale() {
    saleResourceImpl.createSale(A_SALE_REQUEST);

    verify(saleService).createSale(A_SALE_DTO);
  }

}
