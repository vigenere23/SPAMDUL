package ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCardResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCreditsRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.RechargULUseCase;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RechargULResourceImpl implements RechargULResource {

  private final RechargULUseCase rechargULUseCase;
  private final RechargULCardAssembler rechargULCardAssembler;

  public RechargULResourceImpl(RechargULUseCase rechargULUseCase, RechargULCardAssembler rechargULCardAssembler) {
    this.rechargULUseCase = rechargULUseCase;
    this.rechargULCardAssembler = rechargULCardAssembler;
  }

  @Override public Response createCard() {
    RechargULCard card = rechargULUseCase.createCard();
    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.status(Status.CREATED).entity(response).build();
  }

  @Override public Response getCard(String rechargULCardIdString) {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCard card = rechargULUseCase.getRechargULCard(rechargULCardId);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }

  @Override public Response addCredits(String rechargULCardIdString, RechargULCreditsRequest request) {
    double amountDouble = request == null ? null : request.credits;
    Amount amount = Amount.valueOf(amountDouble);
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCard card = rechargULUseCase.addCredits(rechargULCardId, amount);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }
}
