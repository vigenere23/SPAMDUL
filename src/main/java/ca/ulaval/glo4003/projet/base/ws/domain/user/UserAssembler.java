package ca.ulaval.glo4003.projet.base.ws.domain.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  private static final DateTimeFormatter BIRTHDAY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public User create(UserDto userDto) {
    User user = new User();

    user.setName(userDto.name);

    setGender(userDto, user);

    setBirthdayDate(userDto, user);

    setDaysToAccessCampus(userDto, user);

    return user;
  }

  private void setBirthdayDate(UserDto userDto, User user) {
    try {
      user.setBirthday(LocalDate.parse(userDto.birthdayDate, BIRTHDAY_DATE_TIME_FORMATTER));
    } catch (DateTimeParseException e) {
      throw new InvalidBirthdayDateArgumentException("The birthday date provided must be yyyy-MM-dd");
    }
  }

  private void setGender(UserDto userDto, User user) {
    try {
      user.setGender(Gender.valueOf(userDto.gender));
    } catch (IllegalArgumentException e) {
      throw new InvalidGenderArgumentException("The gender provided must be of type male, female or other");
    }
  }

  private void setDaysToAccessCampus(UserDto userDto, User user) {
    try {
      DayOfWeek dayOfWeek = DayOfWeek.valueOf(userDto.dayToAccessCampus.toUpperCase());

      if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
        throw new InvalidDayOfCampusAccessArgumentException("The day entered must be between Monday to Friday");
      }
      user.setDayToAccessCampus(dayOfWeek);
    } catch (IllegalArgumentException e) {
      throw new InvalidDayOfCampusAccessArgumentException("The day entered must be between Monday to Friday");
    }
  }
}
