package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import ca.ulaval.glo4003.spamdul.usecases.fundraising.InitiativeService;
import java.util.List;

public class FundraisingResourceImp implements FundraisingResource {

  private final InitiativeAssembler initiativeAssembler;
  private final InitiativeService initiativeService;

  public FundraisingResourceImp(InitiativeAssembler initiativeAssembler,
                                InitiativeService initiativeService) {
    this.initiativeAssembler = initiativeAssembler;
    this.initiativeService = initiativeService;
  }

  @Override public List<InitiativeResponse> getInitiatives() {
    return initiativeAssembler.toDtos(initiativeService.getAllInitiatives());
  }

  @Override public InitiativeResponse createInitiative(InitiativeRequest request) {
    return initiativeAssembler.toDto(initiativeService.addInitiative(initiativeAssembler.fromDto(request)));
  }
}
