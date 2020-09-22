package ca.ulaval.glo4003.projet.base.ws.domain.user;

import ca.ulaval.glo4003.projet.base.ws.api.user.dto.UserDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserAssembler {

  private static final DateTimeFormatter BIRTHDAY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public User create(UserDto userDto) {

    Gender gender = getGender(userDto);

    LocalDate birthdayDate = getBirthdayDate(userDto);

    DayOfWeek dayToAccessCampus = getDayToAccessCampus(userDto);

    User user = new User();
    user.setName(userDto.name);
    user.setGender(gender);
    user.setBirthday(birthdayDate);
    user.setDayToAccessCampus(dayToAccessCampus);

    return user;
  }

  private Gender getGender(UserDto userDto) {
    try {
      return Gender.valueOf(userDto.gender.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidGenderArgumentException("The gender provided must be of type male, female or other");
    }
  }

  private LocalDate getBirthdayDate(UserDto userDto) {
    try {
      return LocalDate.parse(userDto.birthdayDate, BIRTHDAY_DATE_TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new InvalidBirthdayDateArgumentException("The birthday date provided must be yyyy-MM-dd");
    }
  }


  private DayOfWeek getDayToAccessCampus(UserDto userDto) {
    try {
      DayOfWeek dayOfWeek = DayOfWeek.valueOf(userDto.dayToAccessCampus.toUpperCase());

      if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
        throw new InvalidDayOfCampusAccessArgumentException("The day entered must be between Monday to Friday");
      }

      return dayOfWeek;
    } catch (IllegalArgumentException e) {
      throw new InvalidDayOfCampusAccessArgumentException("The day entered must be between Monday to Friday");
    }
  }
}
