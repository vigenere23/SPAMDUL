package ca.ulaval.glo4003.spamdul.assemblers.user;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.parking.api.campusaccess.dto.user.UserCreationRequest;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.Gender;
import ca.ulaval.glo4003.spamdul.parking.usecases.parkinguser.UserCreationDto;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  private final CarAssembler carAssembler;

  public UserAssembler(CarAssembler carAssembler) {
    this.carAssembler = carAssembler;
  }

  public UserCreationDto fromRequest(UserCreationRequest userCreationRequest) {
    Gender gender = getGender(userCreationRequest);
    LocalDate birthDate = getBirthDate(userCreationRequest);

    UserCreationDto userCreationDto = new UserCreationDto();
    userCreationDto.name = userCreationRequest.name;
    userCreationDto.gender = gender;
    userCreationDto.birthDate = birthDate;

    if (userCreationRequest.car != null) {
      userCreationDto.carDto = carAssembler.fromRequest(userCreationRequest.car);
    }

    return userCreationDto;
  }

  private Gender getGender(UserCreationRequest userCreationRequest) {
    try {
      return Gender.valueOf(userCreationRequest.gender.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidGenderException();
    }
  }

  private LocalDate getBirthDate(UserCreationRequest userCreationRequest) {
    try {
      return LocalDate.parse(userCreationRequest.birthDate, Formatters.DATE_FORMATTER);

    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateException();
    }
  }
}
