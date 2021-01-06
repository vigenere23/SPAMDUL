package ca.ulaval.glo4003.spamdul.invoice.api;

import ca.ulaval.glo4003.spamdul.invoice.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.invoice.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.invoice.entities.InvoiceId;
import ca.ulaval.glo4003.spamdul.invoice.usecases.InvoiceUseCase;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("invoice")
public class InvoiceResource {

  private final InvoiceUseCase invoiceUseCase;
  private final InvoiceDtoAssembler invoiceDtoAssembler;

  public InvoiceResource(InvoiceUseCase invoiceUseCase,
                         InvoiceDtoAssembler invoiceDtoAssembler) {
    this.invoiceUseCase = invoiceUseCase;
    this.invoiceDtoAssembler = invoiceDtoAssembler;
  }

  @Path("{id}")
  @GET
  public Response getInvoice(@PathParam("id") String invoiceIdString) {
    InvoiceId invoiceId = InvoiceId.valueOf(invoiceIdString);
    InvoiceDto invoiceDto = invoiceUseCase.getInvoice(invoiceId);
    InvoiceResponse response = invoiceDtoAssembler.toResponse(invoiceDto);

    return Response.status(200).entity(response).build();
  }

  @Path("{id}/pay")
  @POST
  public Response payInvoice(@PathParam("id") String invoiceIdString) {
    InvoiceId invoiceId = InvoiceId.valueOf(invoiceIdString);
    InvoiceDto invoiceDto = invoiceUseCase.payInvoice(invoiceId);
    InvoiceResponse response = invoiceDtoAssembler.toResponse(invoiceDto);

    return Response.status(200).entity(response).build();
  }
}
