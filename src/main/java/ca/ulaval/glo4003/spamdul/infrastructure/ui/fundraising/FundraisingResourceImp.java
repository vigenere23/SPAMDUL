package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativesResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;

public class FundraisingResourceImp implements FundraisingResource {

  private final InitiativeAssembler initiativeAssembler;
  private final InitiativeService initiativeService;

  public FundraisingResourceImp(InitiativeAssembler initiativeAssembler,
                                InitiativeService initiativeService) {
    this.initiativeAssembler = initiativeAssembler;
    this.initiativeService = initiativeService;
  }

  @Override public InitiativesResponse getInitiatives() {
    return initiativeAssembler.toResponse(initiativeService.getAllInitiatives());
  }

  @Override public InitiativeResponse createInitiative(InitiativeRequest request) {
    return initiativeAssembler.toResponse(initiativeService.addInitiative(initiativeAssembler.fromRequest(request)));
  }
}
