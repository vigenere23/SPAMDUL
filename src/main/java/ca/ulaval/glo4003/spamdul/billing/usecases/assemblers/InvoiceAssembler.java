package ca.ulaval.glo4003.spamdul.billing.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.Invoice;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceDto;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceAssembler {

  private final InvoiceItemAssembler invoiceItemAssembler;

  public InvoiceAssembler(InvoiceItemAssembler invoiceItemAssembler) {
    this.invoiceItemAssembler = invoiceItemAssembler;
  }

  public Set<InvoiceDto> toDtos(Set<Invoice> invoices) {
    return invoices.stream().map(this::toDto).collect(Collectors.toSet());
  }

  public InvoiceDto toDto(Invoice invoice) {
    InvoiceDto dto = new InvoiceDto();
    dto.id = invoice.getId();
    dto.createdAt = invoice.getCreatedAt();
    dto.total = invoice.getTotal();
    dto.status = invoice.getStatus().toString();
    dto.items = invoiceItemAssembler.toDtos(invoice.getItems());

    if (invoice.getPaidAt() != null) {
      dto.paidAt = invoice.getPaidAt();
    }

    return dto;
  }
}
