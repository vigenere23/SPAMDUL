package ca.ulaval.glo4003.spamdul.parking2.usecases.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.entities.access.period.AccessPeriodCreationInfos;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessPeriodCreationDto;

public class AccessPeriodCreationInfosAssembler {

  public AccessPeriodCreationInfos fromDto(AccessPeriodCreationDto dto) {
    return new AccessPeriodCreationInfos(dto.dayOfWeek,
                                         dto.year,
                                         dto.month,
                                         dto.semester,
                                         dto.start,
                                         dto.numberOfHours);
  }
}
