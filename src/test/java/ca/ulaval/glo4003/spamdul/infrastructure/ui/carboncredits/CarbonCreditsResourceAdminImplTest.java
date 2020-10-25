package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsResourceAdminImplTest {

  private CarbonCreditsResourceAdminImpl carbonCreditsResourceAdmin;

  @Mock
  private CarbonCreditsService carbonCreditsService;

  private final double AN_AMOUNT = 12345.34;
  private final boolean IS_ACTIVE = true;

  @Before
  public void setUp() {
    carbonCreditsResourceAdmin = new CarbonCreditsResourceAdminImpl(carbonCreditsService);
  }

  @Test
  public void whenTransferringCredits_shouldCallAndReturnFromService() {
    when(carbonCreditsService.transferRemainingBudget()).thenReturn(AN_AMOUNT);
    CarbonCreditsTransferResponse response = carbonCreditsResourceAdmin.transferFundsToCarbonCredits();
    assertThat(response.transferred).isEqualTo(AN_AMOUNT);
  }
}
