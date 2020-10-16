package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.PassSaleRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.sale.PassSaleAssembler;
import ca.ulaval.glo4003.spamdul.usecases.pass.PassService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class SaleResourceImpl implements SaleResource {

  private PassSaleAssembler passSaleAssembler;
  private PassService passService;

  public SaleResourceImpl(PassService passService, PassSaleAssembler passSaleAssembler) {
    this.passService = passService;
    this.passSaleAssembler = passSaleAssembler;
  }

  public Response sellPass(PassSaleRequest passSaleRequest) {
    this.passService.createPass(passSaleAssembler.fromRequest(passSaleRequest));

    return Response.status(Status.CREATED)
                   .build();
  }
}
