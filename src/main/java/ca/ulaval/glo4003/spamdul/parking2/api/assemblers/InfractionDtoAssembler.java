package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.InfractionResponse;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.InfractionDto;

public class InfractionDtoAssembler {

  public InfractionResponse toResponse(InfractionDto dto) {
    InfractionResponse response = new InfractionResponse();
    response.type = dto.infractionType.toString();
    response.code = dto.code;
    response.amount = dto.amount.asDouble();

    if (dto.invoiceId != null) {
      response.invoiceId = dto.invoiceId.toString();
    }

    return response;
  }
}
