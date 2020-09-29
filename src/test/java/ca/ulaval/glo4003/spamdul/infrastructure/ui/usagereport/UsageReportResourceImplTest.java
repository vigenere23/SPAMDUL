package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;


import static org.mockito.Matchers.any;

import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.RequestReportAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportSummaryDto;
import com.google.common.truth.Truth;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportResourceImplTest {

  @Mock
  private UsageReportService usageReportService;
  @Mock
  private RequestReportAssembler requestReportAssembler;
  @Mock
  private UsageReportDto usageReportDto;

  private ReportRequest reportRequest;
  private ReportCreationDto reportCreationDto;
  private UsageReportSummaryDto usageReportSummaryDto;

  private UsageReportResource usageReportResource;


  @Before
  public void setUp()
      throws Exception {
    usageReportResource = new UsageReportResourceImpl(usageReportService, requestReportAssembler);
  }

  @Test
  public void whenGetUsageReport_thenFoundUsageReportDtoFromService() {
    reportCreationDto = new ReportCreationDto();
    reportRequest = new ReportRequest();
    BDDMockito.given(requestReportAssembler.fromDto(reportRequest)).willReturn(reportCreationDto);
    BDDMockito.given(usageReportService.getReport(reportCreationDto)).willReturn(usageReportDto);

    UsageReportDto dto = usageReportResource.getUsageReport(reportRequest);

    Truth.assertThat(dto).isEqualTo(usageReportDto);
  }

  @Test
  public void whenGetUsageReportSummary_thenFoundUsageReportSummaryDtoFromService() {
    usageReportSummaryDto = new UsageReportSummaryDto();
    BDDMockito.given(usageReportService.getReportSummary(any(LocalDate.class), any(LocalDate.class))).willReturn(usageReportSummaryDto);

    UsageReportSummaryDto dto = usageReportResource.getUsageReportSummary();

    Truth.assertThat(dto).isEqualTo(usageReportSummaryDto );
  }

  @Test
  public void whenGetUsageReportSummary1_thenFoundUsageReportSummaryDtoFromService() {
    usageReportDto = new UsageReportDto();
    BDDMockito.given(usageReportService.getReportMonth(any(LocalDate.class), any(LocalDate.class))).willReturn(usageReportDto);

    UsageReportDto dto = usageReportResource.getUsageReportMonth();

    Truth.assertThat(dto).isEqualTo(usageReportDto );
  }

}
