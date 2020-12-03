package ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user;

import ca.ulaval.glo4003.spamdul.entity.user.Gender;
import ca.ulaval.glo4003.spamdul.infrastructure.ui.campusaccess.dto.user.UserRequest;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.campusaccess.car.CarAssembler;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidBirthDateException;
import ca.ulaval.glo4003.spamdul.interfaceadapters.assemblers.user.exceptions.InvalidGenderException;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.car.CarDto;
import ca.ulaval.glo4003.spamdul.usecases.campusaccess.user.UserDto;
import ca.ulaval.glo4003.spamdul.utils.Formatters;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  private CarAssembler carAssembler;

  public UserAssembler(CarAssembler carAssembler) {
    this.carAssembler = carAssembler;
  }

  public UserDto fromRequest(UserRequest userRequest) {
    //TODO a tester
    CarDto carDto = carAssembler.fromRequest(userRequest.car);

    Gender gender = getGender(userRequest);

    LocalDate birthDate = getBirthDate(userRequest);

    UserDto userDto = new UserDto();
    userDto.name = userRequest.name;
    userDto.gender = gender;
    userDto.birthDate = birthDate;
    userDto.carDto = carDto;

    return userDto;
  }

  private Gender getGender(UserRequest userRequest) {
    try {
      return Gender.valueOf(userRequest.gender.toUpperCase());

    } catch (IllegalArgumentException e) {
      throw new InvalidGenderException("The gender provided must be of type male, female or other");
    }
  }

  private LocalDate getBirthDate(UserRequest userRequest) {
    try {
      return LocalDate.parse(userRequest.birthDate, Formatters.DATE_FORMATTER);

    } catch (DateTimeParseException e) {
      throw new InvalidBirthDateException("The birthday date provided must be yyyy-MM-dd");
    }
  }
}
