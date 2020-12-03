package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;
import ca.ulaval.glo4003.spamdul.entity.user.exceptions.InvalidUserIdFormatException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargULCardResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.rechargul.dto.RechargUlRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.charging.exceptions.InvalidRechargCardUlUserIdFormatException;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.RechargUlDto;

public class RechargULCardAssembler {

  public RechargULCardResponse toResponse(RechargULCard rechargULCard) {
    RechargULCardResponse response = new RechargULCardResponse();
    response.id = rechargULCard.getId().toString();
    response.credits = rechargULCard.total().asDouble();

    return response;
  }

  public RechargUlDto fromRequest(RechargUlRequest rechargUlRequest) {
    //TODO a tester
    try {
      RechargUlDto dto = new RechargUlDto();
      dto.userId = UserId.valueOf(rechargUlRequest.userId);

      return dto;
    } catch (InvalidUserIdFormatException e) {
      throw new InvalidRechargCardUlUserIdFormatException();
    }
  }
}
