package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

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
  public void whenTransferringCredits_shouldCallAndReturnFromService() {
    when(carbonCreditsService.transferRemainingBudget()).thenReturn(AN_AMOUNT);
    CarbonCreditsTransferResponse response = carbonCreditsResource.transferFundsToCarbonCredits();
    assertThat(response.transferred).isEqualTo(AN_AMOUNT);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCallService() {
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    carbonCreditsResource.activateAutomaticTransfer(request);

    verify(carbonCreditsService).activateAutomaticTransfer(IS_ACTIVE);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCReturnResponseWithNoContent() {
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    Response response = carbonCreditsResource.activateAutomaticTransfer(request);

    assertThat(response.getStatus()).isEqualTo(204);
  }
}
