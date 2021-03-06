package ca.ulaval.glo4003.spamdul.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.finance.api.initiatives.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.finance.api.initiatives.dto.InitiativesResponse;
import ca.ulaval.glo4003.spamdul.finance.usecases.initiatives.dto.InitiativeDto;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import java.util.List;
import java.util.stream.Collectors;

public class InitiativeAssembler {

  public InitiativeDto fromRequest(InitiativeRequest request) {
    InitiativeDto initiativeDto = new InitiativeDto();
    initiativeDto.name = request.name;
    initiativeDto.amount = Amount.valueOf(request.amount);

    return initiativeDto;
  }

  public InitiativeResponse toResponse(InitiativeDto initiative) {
    InitiativeResponse response = new InitiativeResponse();
    response.code = initiative.code.toString();
    response.name = initiative.name;
    response.amount = initiative.amount.asDouble();

    return response;
  }

  public InitiativesResponse toResponse(List<InitiativeDto> initiatives) {
    List<InitiativeResponse> items = initiatives.stream().map(this::toResponse).collect(Collectors.toList());
    InitiativesResponse response = new InitiativesResponse();
    response.initiatives = items;

    return response;
  }
}
