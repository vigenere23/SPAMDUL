package ca.ulaval.glo4003.spamdul.api.usagereport;

import ca.ulaval.glo4003.spamdul.assemblers.authentification.AccessTokenCookieAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.UsageReportCreationAssembler;
import ca.ulaval.glo4003.spamdul.entity.authentication.TemporaryToken;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.UsageReportUseCase;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportDto;
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

  private final UsageReportUseCase usageReportUseCase;
  private final UsageReportCreationAssembler usageReportCreationAssembler;
  private final AccessTokenCookieAssembler cookieAssembler;

  public UsageReportResource(UsageReportUseCase usageReportUseCase,
                             UsageReportCreationAssembler usageReportCreationAssembler,
                             AccessTokenCookieAssembler cookieAssembler) {
    this.usageReportUseCase = usageReportUseCase;
    this.usageReportCreationAssembler = usageReportCreationAssembler;
    this.cookieAssembler = cookieAssembler;
  }

  @GET
  @Path("/month")
  @Produces(MediaType.APPLICATION_JSON)
  public UsageReportDto getUsageReport(@QueryParam("startDate") String startDate,
                                       @QueryParam("endDate") String endDate,
                                       @QueryParam("parkingZone") String parkingZone,
                                       @QueryParam("parkingCategory") String parkingCategory,
                                       @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportCreationDto creationDto = usageReportCreationAssembler.fromValues(startDate,
                                                                                 endDate,
                                                                                 parkingZone,
                                                                                 parkingCategory);

    return usageReportUseCase.getReport(creationDto, temporaryToken);
  }

  @GET
  @Path("/summary")
  @Produces(MediaType.APPLICATION_JSON)
  public UsageReportSummaryDto getUsageReportSummary(@QueryParam("startDate") String startDate,
                                                     @QueryParam("endDate") String endDate,
                                                     @QueryParam("parkingZone") String parkingZone,
                                                     @QueryParam("parkingCategory") String parkingCategory,
                                                     @CookieParam("accessToken") Cookie accessToken) {
    TemporaryToken temporaryToken = cookieAssembler.from(accessToken);
    UsageReportCreationDto creationDto = usageReportCreationAssembler.fromValues(startDate,
                                                                                 endDate,
                                                                                 parkingZone,
                                                                                 parkingCategory);

    return usageReportUseCase.getReportSummary(creationDto, temporaryToken);
  }
}
