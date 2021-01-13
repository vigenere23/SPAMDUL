package ca.ulaval.glo4003.spamdul.billing.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.billing.entities.invoice.InvoiceItem;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceItemDto;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceItemAssembler {

  public Set<InvoiceItemDto> toDtos(Set<InvoiceItem> invoiceItems) {
    return invoiceItems.stream().map(this::toDto).collect(Collectors.toSet());
  }

  public InvoiceItemDto toDto(InvoiceItem invoiceItem) {
    InvoiceItemDto dto = new InvoiceItemDto();
    dto.name = invoiceItem.getName();
    dto.quantity = invoiceItem.getQuantity();
    dto.subtotal = invoiceItem.getPrice();

    return dto;
  }
}
