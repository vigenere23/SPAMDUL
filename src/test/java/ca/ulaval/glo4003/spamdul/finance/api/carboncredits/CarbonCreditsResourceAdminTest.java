package ca.ulaval.glo4003.spamdul.finance.api.carboncredits;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.finance.api.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.finance.usecases.carboncredits.CarbonCreditsUseCase;
import ca.ulaval.glo4003.spamdul.shared.entities.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsResourceAdminTest {

  private CarbonCreditsResourceAdmin carbonCreditsResourceAdmin;

  @Mock
  private CarbonCreditsUseCase carbonCreditsUseCase;

  private final Amount AN_AMOUNT = Amount.valueOf(12345.34);

  @Before
  public void setUp() {
    carbonCreditsResourceAdmin = new CarbonCreditsResourceAdmin(carbonCreditsUseCase);
  }

  @Test
  public void whenTransferringCredits_shouldCallAndReturnFromService() {
    when(carbonCreditsUseCase.transferRemainingBudget()).thenReturn(AN_AMOUNT);

    CarbonCreditsTransferResponse response = carbonCreditsResourceAdmin.transferFundsToCarbonCredits();

    assertThat(response.transferred).isEqualTo(AN_AMOUNT.asDouble());
  }
}
