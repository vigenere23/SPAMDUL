package ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.FundraisingRequest;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.fundraising.dto.FundraisingResponse;
import java.util.ArrayList;
import java.util.List;

public class FundraisingResourceImp implements FundraisingResource {

  @Override public List<FundraisingResponse> getInitiatives() {
    return new ArrayList<>();
  }

  @Override public FundraisingResponse createInitiative(FundraisingRequest request) {
    return new FundraisingResponse();
  }
}
