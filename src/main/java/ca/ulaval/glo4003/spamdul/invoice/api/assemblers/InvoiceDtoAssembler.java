package ca.ulaval.glo4003.spamdul.invoice.api.assemblers;

import ca.ulaval.glo4003.spamdul.invoice.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;

public class InvoiceDtoAssembler {

  public InvoiceResponse toResponse(InvoiceDto invoiceDto) {
    InvoiceResponse response = new InvoiceResponse();
    response.id = invoiceDto.id.toString();
    response.createdAt = invoiceDto.createdAt.format(Formatters.DATETIME_FORMATTER);
    response.total = invoiceDto.total.asDouble();
    response.status = invoiceDto.status;

    return response;
  }
}
