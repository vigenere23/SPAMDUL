package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativesResponse;
import java.util.List;
import java.util.stream.Collectors;

public class InitiativeAssembler {

  private final InitiativeFactory initiativeFactory;

  public InitiativeAssembler(InitiativeFactory initiativeFactory) {
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative fromRequest(InitiativeRequest request) {
    return initiativeFactory.create(request.name, request.amount);
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
