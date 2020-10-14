package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.InitiativeResponse;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.fundraising.InitiativeAssembler;
import java.util.ArrayList;
import java.util.List;

public class FundraisingResourceImp implements FundraisingResource {

  private final InitiativeAssembler initiativeAssembler;

  public FundraisingResourceImp(InitiativeAssembler initiativeAssembler) {
    this.initiativeAssembler = initiativeAssembler;
  }

  @Override public List<InitiativeResponse> getInitiatives() {
    return new ArrayList<>();
  }

  @Override public InitiativeResponse createInitiative(InitiativeRequest request) {
    return new InitiativeResponse();
  }
}
