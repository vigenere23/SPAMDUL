package ca.ulaval.glo4003.spamdul.parking2.api.assemblers;

import ca.ulaval.glo4003.spamdul.parking2.api.dtos.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.Sex;
import ca.ulaval.glo4003.spamdul.parking2.usecases.dtos.UserCreationDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import java.time.LocalDate;

public class UserCreationAssembler {

  public UserCreationDto fromRequest(UserCreationRequest request) {
    UserCreationDto dto = new UserCreationDto();
    dto.name = request.name;
    dto.sex = Sex.valueOf(request.sex.toUpperCase());
    dto.birthDate = LocalDate.from(Formatters.DATE_FORMATTER.parse(request.birthDate));

    return dto;
  }
}
