package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.infraction.Infraction;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.InfractionDto;

public class InfractionAssembler {

  public InfractionDto toDto(Infraction infraction) {
    InfractionDto dto = new InfractionDto();
    dto.infractionType = infraction.getInfractionType();
    dto.code = infraction.getInfractionType().getCode();
    dto.amount = infraction.getAmount();

    if (infraction.getInvoiceId() != null) {
      dto.invoiceId = infraction.getInvoiceId();
    }

    return dto;
  }
}
