package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCardResponse;

public class RechargULCardAssembler {

  public RechargULCardResponse toResponse(RechargULCard rechargULCard) {
    RechargULCardResponse response = new RechargULCardResponse();
    response.id = rechargULCard.getId().toString();
    response.credits = rechargULCard.total().asDouble();

    return response;
  }
}
