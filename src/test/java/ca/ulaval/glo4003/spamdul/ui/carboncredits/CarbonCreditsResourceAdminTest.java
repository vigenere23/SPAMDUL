package ca.ulaval.glo4003.spamdul.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import ca.ulaval.glo4003.spamdul.utils.amount.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsResourceAdminTest {

  private CarbonCreditsResourceAdmin carbonCreditsResourceAdmin;

  @Mock
  private CarbonCreditsService carbonCreditsService;

  private final Amount AN_AMOUNT = Amount.valueOf(12345.34);

  @Before
  public void setUp() {
    carbonCreditsResourceAdmin = new CarbonCreditsResourceAdmin(carbonCreditsService);
  }

  @Test
  public void whenTransferringCredits_shouldCallAndReturnFromService() {
    when(carbonCreditsService.transferRemainingBudget()).thenReturn(AN_AMOUNT);
    CarbonCreditsTransferResponse response = carbonCreditsResourceAdmin.transferFundsToCarbonCredits();
    assertThat(response.transferred).isEqualTo(AN_AMOUNT.asDouble());
  }
}
