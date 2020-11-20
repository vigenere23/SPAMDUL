package ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;
import javax.ws.rs.core.Cookie;

public class UsageReportResourceImpl implements UsageReportResource {

  private final UsageReportService usageReportService;
  private final UsageReportCreationAssembler usageReportCreationAssembler;
  private final UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler;
  private final AccessTokenCookieAssembler cookieAssembler;

  public UsageReportResourceImpl(UsageReportService usageReportService,
                                 UsageReportCreationAssembler usageReportCreationAssembler,
                                 UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler,
                                 AccessTokenCookieAssembler cookieAssembler) {
    this.usageReportService = usageReportService;
    this.usageReportCreationAssembler = usageReportCreationAssembler;
    this.usageReportSummaryCreationAssembler = usageReportSummaryCreationAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @Override
  public UsageReportDto getUsageReport(String startDate, String endDate, String parkingZone, Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportCreationDto creationDto = usageReportCreationAssembler.fromValues(startDate,
                                                                                 endDate,
                                                                                 parkingZone);

    return usageReportService.getReport(creationDto, temporaryToken);
  }

  @Override
  public UsageReportSummaryDto getUsageReportSummary(String startDate,
                                                     String endDate,
                                                     String parkingZone,
                                                     Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportSummaryCreationDto creationDto = usageReportSummaryCreationAssembler.fromValues(startDate,
                                                                                               endDate,
                                                                                               parkingZone);

    return usageReportService.getReportSummary(creationDto, temporaryToken);
  }
}
