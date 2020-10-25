package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;

import javax.ws.rs.NotFoundException;

public class NullCarbonCreditsResourceAdmin implements CarbonCreditsResourceAdmin {
    @Override
    public CarbonCreditsTransferResponse transferFundsToCarbonCredits() {
        throw new NotFoundException();
    }
}
