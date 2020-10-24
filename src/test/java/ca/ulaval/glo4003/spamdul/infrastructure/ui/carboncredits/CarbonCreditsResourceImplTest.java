package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarbonCreditsResourceImplTest {

  private CarbonCreditsResourceImpl carbonCreditsResource;

  @Mock
  private CarbonCreditsService carbonCreditsService;

  private final double AN_AMOUNT = 12345.34;
  private final boolean IS_ACTIVE = true;

  @Before
  public void setUp() {
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService);
  }

  @Test
  public void whenGettingTotalTransferredCredits_shouldReturnFromService() {
    when(carbonCreditsService.getTotalCarbonCredits()).thenReturn(AN_AMOUNT);
    CarbonCreditsTransferResponse response = carbonCreditsResource.getAllTransferredCredits();
    Truth.assertThat(response.transferred).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenTransferringCredits_shouldCallAndReturnFromService() {
    when(carbonCreditsService.transferRemainingBudget()).thenReturn(AN_AMOUNT);
    CarbonCreditsTransferResponse response = carbonCreditsResource.transferFundsToCarbonCredits();
    Truth.assertThat(response.transferred).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenTogglingFeature_shouldCallAndReturnReturnFromService() {
    when(carbonCreditsService.setAutomaticTransfer(IS_ACTIVE)).thenReturn(IS_ACTIVE);
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    CarbonCreditsToggleDto response = carbonCreditsResource.toggleAutomaticTransfer(request);

    Truth.assertThat(response.active).isEqualTo(IS_ACTIVE);
  }
}
