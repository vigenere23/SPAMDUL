package ca.ulaval.glo4003.spamdul.billing.api.assemblers;

import ca.ulaval.glo4003.spamdul.billing.api.dtos.InvoiceResponse;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceDtoAssembler {

  private final InvoiceItemDtoAssembler invoiceItemDtoAssembler;

  public InvoiceDtoAssembler(InvoiceItemDtoAssembler invoiceItemDtoAssembler) {
    this.invoiceItemDtoAssembler = invoiceItemDtoAssembler;
  }

  public Set<InvoiceResponse> toResponses(Set<InvoiceDto> dtos) {
    return dtos.stream().map(this::toResponse).collect(Collectors.toSet());
  }

  public InvoiceResponse toResponse(InvoiceDto dto) {
    InvoiceResponse response = new InvoiceResponse();
    response.id = dto.id.toString();
    response.createdAt = dto.createdAt.format(Formatters.DATETIME_FORMATTER);
    response.total = dto.total.asDouble();
    response.status = dto.status;
    response.items = invoiceItemDtoAssembler.toResponses(dto.items);

    if (dto.paidAt != null) {
      response.paidAt = dto.paidAt.format(Formatters.DATETIME_FORMATTER);
    }

    return response;
  }
}
