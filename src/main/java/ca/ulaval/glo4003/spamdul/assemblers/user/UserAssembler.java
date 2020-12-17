package ca.ulaval.glo4003.spamdul.assemblers.user;

import ca.ulaval.glo4003.spamdul.assemblers.parking.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.shared.utils.Formatters;
import ca.ulaval.glo4003.spamdul.api.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  private final CarAssembler carAssembler;

  public UserAssembler(CarAssembler carAssembler) {
    this.carAssembler = carAssembler;
  }

  public UserDto fromRequest(UserRequest userRequest) {
    Gender gender = getGender(userRequest);
    LocalDate birthDate = getBirthDate(userRequest);

    UserDto userDto = new UserDto();
    userDto.name = userRequest.name;
    userDto.gender = gender;
    userDto.birthDate = birthDate;

    if (userRequest.car != null) {
      userDto.carDto = carAssembler.fromRequest(userRequest.car);
    }

    return userDto;
  }

  private Gender getGender(UserRequest userRequest) {
    try {
      return Gender.valueOf(userRequest.gender.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidGenderException();
    }
  }

  private LocalDate getBirthDate(UserRequest userRequest) {
    try {
      return LocalDate.parse(userRequest.birthDate, Formatters.DATE_FORMATTER);

    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateException();
    }
  }
}
