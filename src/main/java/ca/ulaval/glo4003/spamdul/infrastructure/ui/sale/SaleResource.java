package ca.ulaval.glo4003.spamdul.infrastructure.ui.sale;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.sale.dto.SaleRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sale")
public interface SaleResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  Response createSale(SaleRequest saleRequest);
}
