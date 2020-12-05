package ca.ulaval.glo4003.spamdul.infrastructure.ui.pass;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.pass.dto.PassCreationRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.parking.pass.PassAssembler;
import ca.ulaval.glo4003.spamdul.usecases.parking.pass.PassService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PassResourceImpl implements PassResource {

  private final PassAssembler passAssembler;
  private final PassService passService;

  public PassResourceImpl(PassService passService, PassAssembler passAssembler) {
    this.passService = passService;
    this.passAssembler = passAssembler;
  }

  @Override public Response sellPass(PassCreationRequest passCreationRequest) {
    this.passService.createPass(passAssembler.fromRequest(passCreationRequest));

    return Response.status(Status.CREATED)
                   .build();
  }
}
