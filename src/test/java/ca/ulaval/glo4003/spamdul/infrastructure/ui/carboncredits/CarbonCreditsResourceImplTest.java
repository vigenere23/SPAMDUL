package ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.carboncredits.dto.CarbonCreditsToggleDto;
import ca.ulaval.glo4003.spamdul.usecases.carboncredits.CarbonCreditsService;
import javax.ws.rs.core.Cookie;
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
  private static final String TOKEN_CODE = "token_code";
  public static final Cookie A_COOKIE = new Cookie("accessToken", TOKEN_CODE);
  public static final TemporaryToken A_TEMPORARY_TOKEN = TemporaryToken.valueOf(TOKEN_CODE);
  private AccessTokenCookieAssembler cookieAssembler;

  @Before
  public void setUp() {
    cookieAssembler = new AccessTokenCookieAssembler();
    carbonCreditsResource = new CarbonCreditsResourceImpl(carbonCreditsService, cookieAssembler);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCallService() {
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    carbonCreditsResource.activateAutomaticTransfer(request, A_COOKIE);

    verify(carbonCreditsService).activateAutomaticTransfer(IS_ACTIVE, A_TEMPORARY_TOKEN);
  }

  @Test
  public void whenActivateAutomaticTransfer_shouldCReturnResponseWithActiveTrue() {
    when(carbonCreditsService.activateAutomaticTransfer(IS_ACTIVE, A_TEMPORARY_TOKEN)).thenReturn(IS_ACTIVE);
    CarbonCreditsToggleDto request = new CarbonCreditsToggleDto();
    request.active = IS_ACTIVE;

    Response response = carbonCreditsResource.activateAutomaticTransfer(request, A_COOKIE);
    CarbonCreditsToggleDto responseObject = (CarbonCreditsToggleDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(200);
    assertThat(responseObject.active).isEqualTo(IS_ACTIVE);
  }
}
