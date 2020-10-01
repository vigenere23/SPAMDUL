package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.sale.SaleService;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class SaleResourceImpl implements SaleResource {

  private SaleService saleService;
  private PassSaleAssembler passSaleAssembler;

  public SaleResourceImpl(SaleService saleService, PassSaleAssembler passSaleAssembler) {
    this.saleService = saleService;
    this.passSaleAssembler = passSaleAssembler;
  }

  public Response sellPass(PassSaleRequest passSaleRequest) {
    this.saleService.createSale(passSaleAssembler.fromRequest(passSaleRequest));

    return Response.status(Status.CREATED)
                   .build();
  }
}
