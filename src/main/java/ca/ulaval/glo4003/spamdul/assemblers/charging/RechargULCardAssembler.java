package ca.ulaval.glo4003.spamdul.assemblers.charging;

import ca.ulaval.glo4003.spamdul.charging.api.rechargul.dto.RechargULCardResponse;
import ca.ulaval.glo4003.spamdul.charging.usecases.dto.RechargULCardDto;

public class RechargULCardAssembler {

  public RechargULCardResponse toResponse(RechargULCardDto rechargULCard) {
    RechargULCardResponse response = new RechargULCardResponse();
    response.id = rechargULCard.id.toString();
    response.credits = rechargULCard.credits.asDouble();

    return response;
  }
}
