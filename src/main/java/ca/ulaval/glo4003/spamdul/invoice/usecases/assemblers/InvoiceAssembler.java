package ca.ulaval.glo4003.spamdul.invoice.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.invoice.entities.Invoice;
import ca.ulaval.glo4003.spamdul.invoice.usecases.dtos.InvoiceDto;

public class InvoiceAssembler {

  public InvoiceDto toDto(Invoice invoice) {
    InvoiceDto dto = new InvoiceDto();
    dto.id = invoice.getId();
    dto.createdAt = invoice.getCreatedAt();
    dto.total = invoice.getTotal();
    dto.status = invoice.getStatus();

    return dto;
  }
}
