package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.SaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.SaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class SaleResourceImpl implements SaleResource {

  private SaleService saleService;
  private SaleAssembler saleAssembler;

  public SaleResourceImpl(SaleService saleService, SaleAssembler saleAssembler) {
    this.saleService = saleService;
    this.saleAssembler = saleAssembler;
  }

  public Response createSale(SaleRequest saleRequest) {
    this.saleService.createSale(saleAssembler.fromDto(saleRequest));

    return Response.status(Status.CREATED)
                   .build();
  }
}
