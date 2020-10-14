package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising;

import ca.ulaval.glo4003.spamdul.entity.fundraising.Initiative;
import ca.ulaval.glo4003.spamdul.entity.fundraising.InitiativeFactory;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import java.util.List;
import java.util.stream.Collectors;

public class InitiativeAssembler {

  private final InitiativeFactory initiativeFactory;

  public InitiativeAssembler(InitiativeFactory initiativeFactory) {
    this.initiativeFactory = initiativeFactory;
  }

  public Initiative fromDto(InitiativeRequest request) {
    return initiativeFactory.create(request.name, request.amount);
  }

  public InitiativeResponse toDto(Initiative initiative) {
    InitiativeResponse response = new InitiativeResponse();
    response.id = initiative.getId().toString();
    response.name = initiative.getName();
    response.amount = initiative.getAmount();

    return response;
  }

  public List<InitiativeResponse> toDtos(List<Initiative> initiatives) {
    return initiatives.stream().map(this::toDto).collect(Collectors.toList());
  }
}
