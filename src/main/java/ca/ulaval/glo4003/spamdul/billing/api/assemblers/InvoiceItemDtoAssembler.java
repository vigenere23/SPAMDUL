package ca.ulaval.glo4003.spamdul.billing.api.assemblers;

import ca.ulaval.glo4003.spamdul.billing.api.dtos.InvoiceItemResponse;
import ca.ulaval.glo4003.spamdul.billing.usecases.dtos.InvoiceItemDto;
import java.util.Set;
import java.util.stream.Collectors;

public class InvoiceItemDtoAssembler {

  public Set<InvoiceItemResponse> toResponses(Set<InvoiceItemDto> dtos) {
    return dtos.stream().map(this::toResponse).collect(Collectors.toSet());
  }

  public InvoiceItemResponse toResponse(InvoiceItemDto dto) {
    InvoiceItemResponse response = new InvoiceItemResponse();
    response.name = dto.name;
    response.quantity = dto.quantity;
    response.subtotal = dto.subtotal.asDouble();

    return response;
  }
}
