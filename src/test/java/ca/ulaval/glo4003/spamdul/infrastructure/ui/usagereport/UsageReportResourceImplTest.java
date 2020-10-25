package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;


import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsageReportResourceImplTest {

  @Mock
  private UsageReportService usageReportService;
  @Mock
  private UsageReportCreationAssembler usageReportCreationAssembler;
  @Mock
  private UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler;
  @Mock
  private UsageReportDto usageReportDto;
  @Mock
  private UsageReportSummaryDto usageReportSummaryDto;

  private final String START_DATE_STRING = "2020-02-01";
  private final String END_DATE_STRING = "2020-02-11";
  private final String PARKING_ZONE_STRING = "ZONE_1";

  private UsageReportResource usageReportResource;

  @Before
  public void setUp() {
    usageReportResource = new UsageReportResourceImpl(usageReportService,
                                                      usageReportCreationAssembler,
                                                      usageReportSummaryCreationAssembler);
  }

  @Test
  public void whenGetUsageReport_thenFoundUsageReportDtoFromService() {
    UsageReportCreationDto creationDto = new UsageReportCreationDto();
    when(usageReportCreationAssembler.fromValues(START_DATE_STRING, END_DATE_STRING, PARKING_ZONE_STRING))
        .thenReturn(creationDto);
    when(usageReportService.getReport(creationDto)).thenReturn(usageReportDto);

    UsageReportDto dto = usageReportResource.getUsageReport(START_DATE_STRING, END_DATE_STRING, PARKING_ZONE_STRING);

    Truth.assertThat(dto).isEqualTo(usageReportDto);
  }

  @Test
  public void whenGetUsageReportSummary_thenFoundUsageReportSummaryDtoFromService() {
    UsageReportSummaryCreationDto creationDto = new UsageReportSummaryCreationDto();
    when(usageReportSummaryCreationAssembler.fromValues(START_DATE_STRING, END_DATE_STRING, PARKING_ZONE_STRING))
        .thenReturn(creationDto);
    when(usageReportService.getReportSummary(creationDto)).thenReturn(
        usageReportSummaryDto);

    UsageReportSummaryDto dto = usageReportResource.getUsageReportSummary(START_DATE_STRING,
                                                                          END_DATE_STRING,
                                                                          PARKING_ZONE_STRING);

    Truth.assertThat(dto).isEqualTo(usageReportSummaryDto);
  }
}
