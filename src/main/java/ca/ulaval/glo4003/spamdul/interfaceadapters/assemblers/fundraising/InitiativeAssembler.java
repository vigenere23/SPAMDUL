package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativesResponse;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.dto.InitiativeDto;
import java.util.List;
import java.util.stream.Collectors;

public class InitiativeAssembler {

  public InitiativeDto fromRequest(InitiativeRequest request) {
    InitiativeDto initiativeDto = new InitiativeDto();
    initiativeDto.name = request.name;
    initiativeDto.amount = request.amount; // TODO new Amount()
    return initiativeDto;
  }

  public InitiativeResponse toResponse(Initiative initiative) {
    InitiativeResponse response = new InitiativeResponse();
    response.id = initiative.getId().toString();
    response.name = initiative.getName();
    response.amount = initiative.getAmount();

    return response;
  }

  public InitiativesResponse toResponse(List<Initiative> initiatives) {
    List<InitiativeResponse> items = initiatives.stream().map(this::toResponse).collect(Collectors.toList());
    InitiativesResponse response = new InitiativesResponse();
    response.initiatives = items;

    return response;
  }
}
