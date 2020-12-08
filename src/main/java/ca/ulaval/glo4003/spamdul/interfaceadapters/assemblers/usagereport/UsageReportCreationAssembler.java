package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.usagereport.dto.UsageReportCreationDto;
import ca.ulaval.glo4003.spamdul.utils.Formatters;
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

    return parseDate(startDate);
  }

  private LocalDate getEndDate(String endDate) {
    if (endDate == null) {
      return LocalDate.now();
    }

    return parseDate(endDate);
  }

  private LocalDate parseDate(String date) {
    try {
      return LocalDate.parse(date, Formatters.DATE_FORMATTER);

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
