package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestReportAssembler {

  public ReportCreationDto fromDto(String startDate, String endDate, String parkingZone) {
    LocalDate validateStartDate = getStartDate(startDate);
    LocalDate validateEndDate = getEndDate(endDate);
    ParkingZone validateParkingZone = getParkingZone(parkingZone);

    ReportCreationDto reportCreationDTO = new ReportCreationDto();

    reportCreationDTO.startDate = validateStartDate;
    reportCreationDTO.endDate = validateEndDate;
    reportCreationDTO.parkingZone = validateParkingZone;

    return reportCreationDTO;
  }

  private LocalDate getStartDate(String startDate) {
    try {
      return LocalDate.parse(startDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private LocalDate getEndDate(String endDate) {
    try {
      if (endDate == null) {
        return null;
      }
      return LocalDate.parse(endDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private ParkingZone getParkingZone(String parkingZone) {
    try {
      if (parkingZone == null) {
        return null;
      }
      return ParkingZone.valueOf(parkingZone);
    } catch (DateTimeParseException e) {
      throw new InvalidParkingZoneArgumentException("The Parking zone provided must be ZONE_*number*");
    }
  }


}
