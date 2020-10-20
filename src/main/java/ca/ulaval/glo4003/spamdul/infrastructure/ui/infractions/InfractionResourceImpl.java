package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.EmptyPassCodeException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.exceptions.InvalidInfractionPassCodeFormatException;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionValidationDto;

public class InfractionResourceImpl implements InfractionResource {

  private InfractionAssembler infractionAssembler;
  private InfractionService infractionService;

  public InfractionResourceImpl(InfractionAssembler infractionAssembler, InfractionService infractionService) {
    this.infractionAssembler = infractionAssembler;
    this.infractionService = infractionService;
  }

  public InfractionResponse validateParkingPass(InfractionRequest infractionRequest) {
    Infraction infraction = null;
    try {
      InfractionValidationDto infractionValidationDto = infractionAssembler.fromRequest(infractionRequest);
      infraction = infractionService.validatePass(infractionValidationDto);
    } catch (EmptyPassCodeException e) {
      infraction = infractionService.createNoPassInfraction();
    } catch (InvalidInfractionPassCodeFormatException e) {
      infraction = infractionService.createInvalidPassException();
    }

    return infractionAssembler.toResponse(infraction);
  }
}
