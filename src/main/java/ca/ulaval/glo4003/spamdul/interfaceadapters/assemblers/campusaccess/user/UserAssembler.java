package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.Formatters;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  public UserDto fromRequest(UserRequest userRequest) {

    Gender gender = getGender(userRequest);

    LocalDate birthDate = getBirthDate(userRequest);

    UserDto userDto = new UserDto();
    userDto.name = userRequest.name;
    userDto.gender = gender;
    userDto.birthDate = birthDate;

    return userDto;
  }

  private Gender getGender(UserRequest userRequest) {
    try {
      return Gender.valueOf(userRequest.gender.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidGenderArgumentException();
    }
  }

  private LocalDate getBirthDate(UserRequest userRequest) {
    try {
      return LocalDate.parse(userRequest.birthDate, Formatters.DATE_FORMATTER);

    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateArgumentException();
    }
  }
}
