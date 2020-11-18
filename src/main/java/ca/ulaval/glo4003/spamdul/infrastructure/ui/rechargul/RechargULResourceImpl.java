package ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCardId;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCardResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCreditsRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.RechargULCardAssembler;
import ca.ulaval.glo4003.spamdul.usecases.charging.ChargingUseCase;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import javax.ws.rs.core.Response;

public class RechargULResourceImpl implements RechargULResource {

  private final ChargingUseCase chargingUseCase;
  private final RechargULCardAssembler rechargULCardAssembler;

  public RechargULResourceImpl(ChargingUseCase chargingUseCase,
                               RechargULCardAssembler rechargULCardAssembler) {
    this.chargingUseCase = chargingUseCase;
    this.rechargULCardAssembler = rechargULCardAssembler;
  }

  @Override public Response getCard(String rechargULCardIdString) {
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCard card = chargingUseCase.getRechargeULCard(rechargULCardId);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }

  @Override public Response addCredits(String rechargULCardIdString, RechargULCreditsRequest request) {
    double amountDouble = request == null ? null : request.credits;
    Amount amount = Amount.valueOf(amountDouble);
    RechargULCardId rechargULCardId = RechargULCardId.valueOf(rechargULCardIdString);

    RechargULCard card = chargingUseCase.addCredits(rechargULCardId, amount);

    RechargULCardResponse response = rechargULCardAssembler.toResponse(card);
    return Response.ok(response).build();
  }
}
