package ca.ulaval.glo4003.spamdul.ui.usagereport;

import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.ui.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.UsageReportSummaryCreationAssembler;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportService;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryDto;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

@Path("/usage-report")
public class UsageReportResource {

  private final UsageReportService usageReportService;
  private final UsageReportCreationAssembler usageReportCreationAssembler;
  private final UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler;
  private final AccessTokenCookieAssembler cookieAssembler;

  public UsageReportResource(UsageReportService usageReportService,
                             UsageReportCreationAssembler usageReportCreationAssembler,
                             UsageReportSummaryCreationAssembler usageReportSummaryCreationAssembler,
                             AccessTokenCookieAssembler cookieAssembler) {
    this.usageReportService = usageReportService;
    this.usageReportCreationAssembler = usageReportCreationAssembler;
    this.usageReportSummaryCreationAssembler = usageReportSummaryCreationAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @GET
  @Path("/month")
  @Produces(MediaType.APPLICATION_JSON)
  public UsageReportDto getUsageReport(@QueryParam("startDate") String startDate,
                                       @QueryParam("endDate") String endDate,
                                       @QueryParam("parkingZone") String parkingZone,
                                       @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportCreationDto creationDto = usageReportCreationAssembler.fromValues(startDate,
                                                                                 endDate,
                                                                                 parkingZone);

    return usageReportService.getReport(creationDto, temporaryToken);
  }

  @GET
  @Path("/summary")
  @Produces(MediaType.APPLICATION_JSON)
  public UsageReportSummaryDto getUsageReportSummary(@QueryParam("startDate") String startDate,
                                                     @QueryParam("endDate") String endDate,
                                                     @QueryParam("parkingZone") String parkingZone,
                                                     @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportSummaryCreationDto creationDto = usageReportSummaryCreationAssembler.fromValues(startDate,
                                                                                               endDate,
                                                                                               parkingZone);

    return usageReportService.getReportSummary(creationDto, temporaryToken);
  }
}
