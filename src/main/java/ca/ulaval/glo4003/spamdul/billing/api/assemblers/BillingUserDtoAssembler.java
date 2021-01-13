package ca.ulaval.glo4003.spamdul.billing.api.assemblers;

import ca.ulaval.glo4003.spamdul.billing.api.dtos.BillingUserResponse;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.BillingUserDto;

public class BillingUserDtoAssembler {

  private final InvoiceDtoAssembler invoiceDtoAssembler;

  public BillingUserDtoAssembler(InvoiceDtoAssembler invoiceDtoAssembler) {
    this.invoiceDtoAssembler = invoiceDtoAssembler;
  }

  public BillingUserResponse toResponse(BillingUserDto dto) {
    BillingUserResponse response = new BillingUserResponse();
    response.accountId = dto.accountId.toString();
    response.invoices = invoiceDtoAssembler.toResponses(dto.invoices);

    return response;
  }
}
