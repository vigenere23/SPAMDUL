package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UsageReportCreationAssembler {

  public UsageReportCreationDto fromValues(String startDate, String endDate, String parkingZone) {
    LocalDate validStartDate = getStartDate(startDate);
    LocalDate validEndDate = getEndDate(endDate);
    ParkingZone validParkingZone = getParkingZone(parkingZone);

    UsageReportCreationDto usageReportCreationDto = new UsageReportCreationDto();

    usageReportCreationDto.startDate = validStartDate;
    usageReportCreationDto.endDate = validEndDate;
    usageReportCreationDto.parkingZone = validParkingZone;

    return usageReportCreationDto;
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
      return LocalDate.now();
    }

    try {
      return LocalDate.parse(endDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private ParkingZone getParkingZone(String parkingZone) {
    if (parkingZone == null) {
      return null;
    }

    try {
      return ParkingZone.valueOf(parkingZone.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidParkingZoneArgumentException("The Parking zone provided must be ZONE_*number*");
    }
  }
}
