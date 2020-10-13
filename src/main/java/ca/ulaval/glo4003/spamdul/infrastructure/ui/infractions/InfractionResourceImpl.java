package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.InfractionService.InfractionService;
import ca.ulaval.glo4003.spamdul.usecases.InfractionService.InfractionValidationDto;

public class InfractionResourceImpl implements InfractionResource {

  private InfractionAssembler infractionAssembler;
  private InfractionService infractionService;

  public InfractionResourceImpl(InfractionAssembler infractionAssembler, InfractionService infractionService) {
    this.infractionAssembler = infractionAssembler;
    this.infractionService = infractionService;
  }

  public InfractionResponse validateParkingPass(InfractionRequest infractionRequest) {
    InfractionValidationDto infractionValidationDto = infractionAssembler.fromRequest(infractionRequest);
    Infraction infraction = infractionService.validatePass(infractionValidationDto);

    return infractionAssembler.toResponse(infraction);
  }
}
