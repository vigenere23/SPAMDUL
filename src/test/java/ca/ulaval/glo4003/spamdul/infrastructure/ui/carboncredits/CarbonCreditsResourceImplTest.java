package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsTransferResponse;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import javax.ws.rs.core.Response;
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

  private final boolean IS_ACTIVE = true;

  @Before
  public void setUp() {
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCallService() {
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    carbonCreditsResource.activateAutomaticTransfer(request);

    verify(carbonCreditsService).activateAutomaticTransfer(IS_ACTIVE);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCReturnResponseWithActiveTrue() {
    when(carbonCreditsService.activateAutomaticTransfer(IS_ACTIVE)).thenReturn(IS_ACTIVE);
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    Response response = carbonCreditsResource.activateAutomaticTransfer(request);
    CarbonCreditsToggleDto responseObject = (CarbonCreditsToggleDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(responseObject.active).isEqualTo(IS_ACTIVE);
  }
}
