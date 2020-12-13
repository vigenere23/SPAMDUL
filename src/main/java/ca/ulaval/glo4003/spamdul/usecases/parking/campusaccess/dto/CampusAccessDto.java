package ca.ulaval.glo4003.spamdul.usecases.parking.campusaccess.dto;

import ca.ulaval.glo4003.spamdul.entity.parking.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class CampusAccessDto {

  public UserId userId;
  public TimePeriodDto timePeriodDto;
  public CampusAccessCode code;
}
