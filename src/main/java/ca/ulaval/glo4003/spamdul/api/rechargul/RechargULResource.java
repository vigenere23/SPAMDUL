package ca.ulaval.glo4003.spamdul.api.rechargul;

import ca.ulaval.glo4003.spamdul.api.rechargul.dto.RechargULCardResponse;
import ca.ulaval.glo4003.spamdul.api.rechargul.dto.RechargULCreditsRequest;
import ca.ulaval.glo4003.spamdul.api.rechargul.dto.RechargULRequest;
import ca.ulaval.glo4003.spamdul.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.shared.amount.Amount;
import ca.ulaval.glo4003.spamdul.usecases.charging.RechargULUseCase;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.RechargULCardDto;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/rechargul")
public class RechargULResource {

  private final RechargULUseCase rechargULUseCase;
  private final RechargULCardAssembler rechargULCardAssembler;

  public RechargULResource(RechargULUseCase rechargULUseCase, RechargULCardAssembler rechargULCardAssembler) {
    this.rechargULUseCase = rechargULUseCase;
    this.rechargULCardAssembler = rechargULCardAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createCard(RechargULRequest rechargUlRequest) {
    UserId userId = UserId.valueOf(rechargUlRequest.userId);
    RechargULCardDto card = rechargULUseCase.createCard(userId);
    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);

    return Response.status(Status.CREATED).entity(response).build();
  }

  @Path("/{id}")
  @GET
  public Response getCard(@PathParam("id") String rechargULCardIdString) {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCardDto card = rechargULUseCase.getRechargULCard(rechargULCardId);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }

  @Path("/{id}/credits")
  @POST
  public Response addCredits(@PathParam("id") String rechargULCardIdString, RechargULCreditsRequest request) {
    double amountDouble = request == null ? null : request.credits;
    Amount amount = Amount.valueOf(amountDouble);
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCardDto card = rechargULUseCase.addCredits(rechargULCardId, amount);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }
}
