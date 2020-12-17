package ca.ulaval.glo4003.spamdul.assemblers.finance;

import ca.ulaval.glo4003.spamdul.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.finance.usecases.revenue.dto.TransactionQueryDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Calendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TransactionQueryAssembler {

  private final Calendar calendar;

  public TransactionQueryAssembler(Calendar calendar) {
    this.calendar = calendar;
  }

  public TransactionQueryDto fromValues(String startDate, String endDate) {
    TransactionQueryDto dto = new TransactionQueryDto();
    dto.startDate = getStartDate(startDate);
    dto.endDate = getEndDate(endDate);

    return dto;
  }

  private LocalDateTime getStartDate(String startDate) {
    if (startDate == null) {
      return calendar.getStartOfSchoolYearAtDate(LocalDate.now());
    }

    return parseDate(startDate, LocalTime.MIN);
  }

  private LocalDateTime getEndDate(String endDate) {
    if (endDate == null) {
      return calendar.getEndOfSchoolYearAtDate(LocalDate.now());
    }

    return parseDate(endDate, LocalTime.MAX);
  }

  private LocalDateTime parseDate(String stringDate, LocalTime time) {
    try {
      LocalDate date = LocalDate.parse(stringDate, Formatters.DATE_FORMATTER);

      return LocalDateTime.of(date, time);

    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException();
    }
  }
}
