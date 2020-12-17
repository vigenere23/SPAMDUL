package ca.ulaval.glo4003.spamdul.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.exceptions.InvalidParkingCategoryArgumentException;
import ca.ulaval.glo4003.spamdul.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingCategory;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import ca.ulaval.glo4003.spamdul.usage.usecases.usagereport.dto.UsageReportCreationDto;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UsageReportCreationAssembler {

  public UsageReportCreationDto fromValues(String startDate,
                                           String endDate,
                                           String parkingZone,
                                           String parkingCategory) {
    LocalDate validStartDate = getStartDate(startDate);
    LocalDate validEndDate = getEndDate(endDate);
    ParkingZone validParkingZone = getParkingZone(parkingZone);
    ParkingCategory validParkingCategory = getParkingCategory(parkingCategory);

    UsageReportCreationDto usageReportCreationDto = new UsageReportCreationDto();

    usageReportCreationDto.startDate = validStartDate;
    usageReportCreationDto.endDate = validEndDate;
    usageReportCreationDto.parkingZone = validParkingZone;
    usageReportCreationDto.parkingCategory = validParkingCategory;

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

  private ParkingCategory getParkingCategory(String parkingCategory) {
    if (parkingCategory == null) {
      return null;
    }

    try {
      return ParkingCategory.valueOf(parkingCategory.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidParkingCategoryArgumentException();
    }
  }
}
