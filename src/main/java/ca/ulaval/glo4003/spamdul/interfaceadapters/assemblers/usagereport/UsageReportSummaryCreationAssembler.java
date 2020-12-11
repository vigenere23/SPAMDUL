package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportSummaryCreationDto;
import ca.ulaval.glo4003.spamdul.utils.Formatters;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UsageReportSummaryCreationAssembler {

  public UsageReportSummaryCreationDto fromValues(String startDate, String endDate, String parkingZone) {
    LocalDate validStartDate = getStartDate(startDate);
    LocalDate validEndDate = getEndDate(endDate);
    ParkingZone validParkingZone = getParkingZone(parkingZone);

    UsageReportSummaryCreationDto usageReportSummaryCreationDto = new UsageReportSummaryCreationDto();

    usageReportSummaryCreationDto.startDate = validStartDate;
    usageReportSummaryCreationDto.endDate = validEndDate;
    usageReportSummaryCreationDto.parkingZone = validParkingZone;

    return usageReportSummaryCreationDto;
  }

  private LocalDate getStartDate(String startDate) {
    if (startDate == null) {
      return LocalDate.now().withDayOfMonth(1);
    }

    try {
      return LocalDate.parse(startDate, Formatters.DATE_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException();
    }
  }

  private LocalDate getEndDate(String endDate) {
    if (endDate == null) {
      return LocalDate.now();
    }

    try {
      return LocalDate.parse(endDate, Formatters.DATE_FORMATTER);

    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException();
    }
  }

  private ParkingZone getParkingZone(String parkingZone) {
    if (parkingZone == null) {
      return null;
    }

    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneArgumentException();
    }
  }
}
