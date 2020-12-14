package ca.ulaval.glo4003.spamdul.usecases.charging.assembler;

import ca.ulaval.glo4003.spamdul.entity.rechargul.RechargULCard;
import ca.ulaval.glo4003.spamdul.usecases.charging.dto.RechargULCardDto;

public class RechargULDtoAssembler {

  public RechargULCardDto toDto(RechargULCard rechargULCard) {
    RechargULCardDto rechargULCardDto = new RechargULCardDto();
    rechargULCardDto.id = rechargULCard.getId();
    rechargULCardDto.credits = rechargULCard.total();

    return rechargULCardDto;
  }

}
