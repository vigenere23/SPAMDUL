package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.accesscampus.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.accesscampus.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
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
      throw new InvalidGenderArgumentException("The gender provided must be of type male, female or other");
    }
  }

  private LocalDate getBirthDate(UserRequest userRequest) {
    try {
      return LocalDate.parse(userRequest.birthDate, DateTimeFormatter.BIRTHDAY_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateArgumentException("The birthday date provided must be yyyy-MM-dd");
    }
  }
}
