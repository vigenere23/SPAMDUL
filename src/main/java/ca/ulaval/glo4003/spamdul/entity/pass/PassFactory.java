package ca.ulaval.glo4003.spamdul.entity.pass;

import ca.ulaval.glo4003.spamdul.entity.calendar.Calendar;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PassFactory {

  public PassFactory() {
  }

  public Pass create(UserId userId, ParkingZone parkingZone, PassType passType) {
    PassCode passCode = new PassCode();

    return new Pass(passCode, userId, parkingZone, passType);
  }
}
