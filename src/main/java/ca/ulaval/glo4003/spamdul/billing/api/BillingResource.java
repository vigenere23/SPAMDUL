package ca.ulaval.glo4003.spamdul.billing.api;

import ca.ulaval.glo4003.spamdul.account.entities.AccountId;
import ca.ulaval.glo4003.spamdul.billing.api.assemblers.BillingUserDtoAssembler;
import ca.ulaval.glo4003.spamdul.billing.api.assemblers.InvoiceDtoAssembler;
import ca.ulaval.glo4003.spamdul.billing.api.dtos.BillingUserResponse;
import ca.ulaval.glo4003.spamdul.billing.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceId;
import ca.ulaval.glo4003.spamdul.billing.usecases.BillingUseCase;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.BillingUserDto;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceDto;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("billing")
public class BillingResource {

  private final BillingUseCase billingUseCase;
  private final BillingUserDtoAssembler billingUserDtoAssembler;
  private final InvoiceDtoAssembler invoiceDtoAssembler;

  public BillingResource(BillingUseCase billingUseCase,
                         BillingUserDtoAssembler billingUserDtoAssembler,
                         InvoiceDtoAssembler invoiceDtoAssembler) {
    this.billingUseCase = billingUseCase;
    this.billingUserDtoAssembler = billingUserDtoAssembler;
    this.invoiceDtoAssembler = invoiceDtoAssembler;
  }

  @Path("user/{accountId}")
  @GET
  public Response getUser(@PathParam("accountId") String accountIdString) {
    AccountId accountId = AccountId.valueOf(accountIdString);
    BillingUserDto dto = billingUseCase.getUser(accountId);
    BillingUserResponse response = billingUserDtoAssembler.toResponse(dto);

    return Response.status(200).entity(response).build();
  }

  @Path("invoice/{id}/pay")
  @POST
  public Response payInvoice(@PathParam("id") String invoiceIdString) {
    InvoiceId invoiceId = InvoiceId.valueOf(invoiceIdString);
    InvoiceDto dto = billingUseCase.payInvoice(invoiceId);
    InvoiceResponse response = invoiceDtoAssembler.toResponse(dto);

    return Response.status(200).entity(response).build();
  }
}
