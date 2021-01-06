package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.accessright.AccessPeriodCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodType;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessPeriodCreationDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.Semester;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class AccessPeriodCreationAssembler {

  public AccessPeriodCreationDto fromRequest(AccessPeriodCreationRequest request) {
    AccessPeriodCreationDto dto = new AccessPeriodCreationDto();

    dto.type = AccessPeriodType.valueOf(request.type.toUpperCase());
    dto.year = request.year;
    dto.numberOfHours = request.numberOfHours;

    if (request.startTime != null) {
      dto.startTime = LocalTime.parse(request.startTime, Formatters.TIME_FORMATTER);
    }
    if (request.date != null) {
      dto.date = LocalDate.parse(request.date, Formatters.DATE_FORMATTER);
    }
    if (request.dayOfWeek != null) {
      dto.dayOfWeek = DayOfWeek.valueOf(request.dayOfWeek.toUpperCase());
    }
    if (request.month != 0) {
      dto.month = Month.of(request.month);
    }
    if (request.semester != null) {
      dto.semester = Semester.valueOf(request.semester.toUpperCase());
    }

    return dto;
  }
}
