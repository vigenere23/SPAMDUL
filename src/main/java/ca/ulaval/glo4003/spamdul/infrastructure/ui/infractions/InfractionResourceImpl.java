package ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions;

import ca.ulaval.glo4003.spamdul.entity.infractions.Infraction;
import ca.ulaval.glo4003.spamdul.entity.infractions.PassToValidateDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionPaymentRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.infractions.dto.InfractionResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.infraction.InfractionAssembler;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionPaymentDto;
import ca.ulaval.glo4003.spamdul.usecases.infraction.InfractionService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class InfractionResourceImpl implements InfractionResource {

  private final InfractionAssembler infractionAssembler;
  private final InfractionService infractionService;

  public InfractionResourceImpl(InfractionAssembler infractionAssembler, InfractionService infractionService) {
    this.infractionAssembler = infractionAssembler;
    this.infractionService = infractionService;
  }

  public Response validateParkingPass(InfractionRequest infractionRequest) {
    PassToValidateDto passToValidateDto = infractionAssembler.fromRequest(infractionRequest);
    Infraction infraction = infractionService.giveInfractionIfNotValid(passToValidateDto);

    InfractionResponse infractionResponse = infractionAssembler.toResponse(infraction);

    if (infractionResponse == null) {
      return Response
          .status(Status.NO_CONTENT)
          .build();

    } else {
      return Response
          .status(Status.OK)
          .entity(infractionResponse)
          .build();
    }
  }

  public Response payInfraction(InfractionPaymentRequest infractionPaymentRequest) {
    InfractionPaymentDto infractionPaymentDto = infractionAssembler.fromRequest(infractionPaymentRequest);
    infractionService.payInfraction(infractionPaymentDto);

    return Response.status(Status.OK)
                   .build();
  }
}
