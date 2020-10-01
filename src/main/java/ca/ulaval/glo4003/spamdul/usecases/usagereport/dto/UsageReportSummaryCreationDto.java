package ca.ulaval.glo4003.spamdul.usecases.usagereport.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class UsageReportSummaryCreationDto {

  @NotNull
  public LocalDate startDate;
  @NotNull
  public LocalDate endDate;
}
