package ca.ulaval.glo4003.spamdul.invoice.api.assemblers;

import ca.ulaval.glo4003.spamdul.invoice.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;

public class InvoiceDtoAssembler {

  public InvoiceResponse toResponse(InvoiceDto dto) {
    InvoiceResponse response = new InvoiceResponse();
    response.id = dto.id.toString();
    response.createdAt = dto.createdAt.format(Formatters.DATETIME_FORMATTER);
    response.total = dto.total.asDouble();
    response.status = dto.status;

    if (dto.paidAt != null) {
      response.paidAt = dto.paidAt.format(Formatters.DATETIME_FORMATTER);
    }

    return response;
  }
}
