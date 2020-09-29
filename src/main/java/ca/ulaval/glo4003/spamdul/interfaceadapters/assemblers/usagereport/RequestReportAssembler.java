package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport;

import ca.ulaval.glo4003.spamdul.entity.parkingaccesslog.ParkingZone;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.RequestReport;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.usagereport.exceptions.InvalidParkingZoneArgumentException;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.usagereport.dto.RequestReportDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestReportAssembler {

  public RequestReportDto fromDto(RequestReport requestReport) {
    LocalDate startDate = getStartDate(requestReport);
    LocalDate endDate = getEndDate(requestReport);
    ParkingZone parkingZone = getParkingZone(requestReport);

    RequestReportDto requestReportDTO = new RequestReportDto();

    requestReportDTO.startDate = startDate;
    requestReportDTO.endDate = endDate;
    requestReportDTO.parkingZone = parkingZone;

    return requestReportDTO;
  }

  private LocalDate getStartDate(RequestReport requestReport) {
    try {
      return LocalDate.parse(requestReport.startDate, DateTimeFormatter.DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private LocalDate getEndDate(RequestReport requestReport) {
    try {
      if (requestReport.endDate == null) {
        return null;
      }
      return LocalDate.parse(requestReport.endDate, DateTimeFormatter.DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidDateArgumentException("The date provided must be yyyy-MM-dd");
    }
  }

  private ParkingZone getParkingZone(RequestReport requestReport) {
    try {
      if (requestReport.parkingZone == null) {
        return null;
      }
      return ParkingZone.valueOf(requestReport.parkingZone);
    } catch (DateTimeParseException e) {
      throw new InvalidParkingZoneArgumentException("The Parking zone provided must be ZONE_*number*");
    }
  }


}
