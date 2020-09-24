package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.user.dto.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidDayOfCampusAccessArgumentException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderArgumentException;
import ca.ulaval.glo4003.spamdul.usecases.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  public UserDto fromDto(UserRequest userRequest) {

    Gender gender = getGender(userRequest);

    LocalDate birthDate = getBirthDate(userRequest);

    DayOfWeek dayToAccessCampus = getDayToAccessCampus(userRequest);

    UserDto userDto = new UserDto();
    userDto.name = userRequest.name;
    userDto.gender = gender;
    userDto.birthDate = birthDate;
    userDto.dayToAccessCampus = dayToAccessCampus;

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


  private DayOfWeek getDayToAccessCampus(UserRequest userRequest) {
    try {
      return DayOfWeek.valueOf(userRequest.dayToAccessCampus.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidDayOfCampusAccessArgumentException("The day entered must be a valid day of the week");
    }
  }
}