package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportCreationDto;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.ReportRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestReportAssembler {

  public ReportCreationDto fromDto(ReportRequest reportRequest) {
    LocalDate startDate = getStartDate(reportRequest);
    LocalDate endDate = getEndDate(reportRequest);
    ParkingZone parkingZone = getParkingZone(reportRequest);

    ReportCreationDto reportCreationDTO = new ReportCreationDto();

    reportCreationDTO.startDate = startDate;
    reportCreationDTO.endDate = endDate;
    reportCreationDTO.parkingZone = parkingZone;

    return reportCreationDTO;
  }

  private LocalDate getStartDate(ReportRequest reportRequest) {
    try {
      return LocalDate.parse(reportRequest.startDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private LocalDate getEndDate(ReportRequest reportRequest) {
    try {
      if (reportRequest.endDate == null) {
        return null;
      }
      return LocalDate.parse(reportRequest.endDate, DateTimeFormatter.USAGE_REPORT_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private ParkingZone getParkingZone(ReportRequest reportRequest) {
    try {
      if (reportRequest.parkingZone == null) {
        return null;
      }
      return ParkingZone.valueOf(reportRequest.parkingZone);
    } catch (DateTimeParseException e) {
      throw new InvalidParkingZoneArgumentException("The Parking zone provided must be ZONE_*number*");
    }
  }


}
