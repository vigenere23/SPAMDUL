package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UsageReportSummaryCreationAssembler {

  public UsageReportSummaryCreationDto fromValues(String startDate, String endDate) {
    LocalDate validStartDate = getStartDate(startDate);
    LocalDate validEndDate = getEndDate(endDate);

    UsageReportSummaryCreationDto usageReportSummaryCreationDto = new UsageReportSummaryCreationDto();

    usageReportSummaryCreationDto.startDate = validStartDate;
    usageReportSummaryCreationDto.endDate = validEndDate;

    return usageReportSummaryCreationDto;
  }

  private LocalDate getStartDate(String startDate) {
    if (startDate == null) {
      return LocalDate.now().withDayOfMonth(1);
    }

    try {
      return LocalDate.parse(startDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private LocalDate getEndDate(String endDate) {
    if (endDate == null) {
      LocalDate now = LocalDate.now();
      LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
      return endOfMonth.isAfter(now) ? now : endOfMonth;
    }

    try {
      return LocalDate.parse(endDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }
}
