package ca.ulaval.glo4003.spamdul.charging.usecases.assembler;

import ca.ulaval.glo4003.spamdul.charging.entities.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.charging.usecases.dto.RechargULCardDto;

public class RechargULDtoAssembler {

  public RechargULCardDto toDto(RechargULCard rechargULCard) {
    RechargULCardDto rechargULCardDto = new RechargULCardDto();
    rechargULCardDto.id = rechargULCard.getId();
    rechargULCardDto.credits = rechargULCard.total();

    return rechargULCardDto;
  }

}
