package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.AccessRightResponse;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.AccessRightDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import java.util.Set;
import java.util.stream.Collectors;

public class AccessRightDtoAssembler {

  public Set<AccessRightResponse> toResponses(Set<AccessRightDto> dtos) {
    return dtos.stream().map(this::toResponse).collect(Collectors.toSet());
  }

  public AccessRightResponse toResponse(AccessRightDto dto) {
    AccessRightResponse response = new AccessRightResponse();
    response.periodStart = dto.periodStart.format(Formatters.DATE_FORMATTER);
    response.periodEnd = dto.periodEnd.format(Formatters.DATE_FORMATTER);
    response.startTime = dto.startTime.format(Formatters.TIME_FORMATTER);
    response.endTime = dto.endTime.format(Formatters.TIME_FORMATTER);

    if (dto.dayOfWeek != null) {
      response.dayOfWeek = dto.dayOfWeek.toString();
    }

    return response;
  }
}
