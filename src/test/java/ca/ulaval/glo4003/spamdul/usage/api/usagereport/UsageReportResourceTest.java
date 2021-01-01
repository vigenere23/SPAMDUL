package ca.ulaval.glo4003.spamdul.usage.api.usagereport;


import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.authentication.entities.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.UsageReportUseCase;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportSummaryDto;
import com.google.common.truth.Truth;
import javax.ws.rs.core.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportResourceTest {

  @Mock
  private UsageReportUseCase usageReportUseCase;
  @Mock
  private UsageReportCreationAssembler usageReportCreationAssembler;
  @Mock
  private UsageReportDto usageReportDto;
  @Mock
  private UsageReportSummaryDto usageReportSummaryDto;

  private final String START_DATE_STRING = "2020-02-01";
  private final String END_DATE_STRING = "2020-02-11";
  private final String PARKING_ZONE_STRING = "ZONE_1";
  private final String PARKING_CATEGORY_STRING = "CAR";
  public static final String TOKEN_CODE = "token_code";
  public static final Cookie A_COOKIE = new Cookie("accessToken", TOKEN_CODE);
  public static final TemporaryToken A_TEMPORARY_TOKEN = TemporaryToken.valueOf(TOKEN_CODE);
  private AccessTokenCookieAssembler cookieAssembler;

  private UsageReportResource usageReportResource;

  @Before
  public void setUp() {
    cookieAssembler = new AccessTokenCookieAssembler();
    usageReportResource = new UsageReportResource(usageReportUseCase,
                                                  usageReportCreationAssembler,
                                                  cookieAssembler);
  }

  @Test
  public void whenGetUsageReport_thenFoundUsageReportDtoFromService() {
    UsageReportCreationDto creationDto = new UsageReportCreationDto();
    when(usageReportCreationAssembler.fromValues(START_DATE_STRING,
                                                 END_DATE_STRING,
                                                 PARKING_ZONE_STRING,
                                                 PARKING_CATEGORY_STRING))
        .thenReturn(creationDto);
    when(usageReportUseCase.getReport(creationDto, A_TEMPORARY_TOKEN)).thenReturn(usageReportDto);

    UsageReportDto dto = usageReportResource.getUsageReport(START_DATE_STRING,
                                                            END_DATE_STRING,
                                                            PARKING_ZONE_STRING,
                                                            PARKING_CATEGORY_STRING,
                                                            A_COOKIE);

    Truth.assertThat(dto).isEqualTo(usageReportDto);
  }

  @Test
  public void whenGetUsageReportSummary_thenFoundUsageReportSummaryDtoFromService() {
    UsageReportCreationDto creationDto = new UsageReportCreationDto();
    when(usageReportCreationAssembler.fromValues(START_DATE_STRING,
                                                 END_DATE_STRING,
                                                 PARKING_ZONE_STRING,
                                                 PARKING_CATEGORY_STRING))
        .thenReturn(creationDto);
    when(usageReportUseCase.getReportSummary(creationDto, A_TEMPORARY_TOKEN)).thenReturn(
        usageReportSummaryDto);

    UsageReportSummaryDto dto = usageReportResource.getUsageReportSummary(START_DATE_STRING,
                                                                          END_DATE_STRING,
                                                                          PARKING_ZONE_STRING,
                                                                          PARKING_CATEGORY_STRING,
                                                                          A_COOKIE);

    Truth.assertThat(dto).isEqualTo(usageReportSummaryDto);
  }
}
