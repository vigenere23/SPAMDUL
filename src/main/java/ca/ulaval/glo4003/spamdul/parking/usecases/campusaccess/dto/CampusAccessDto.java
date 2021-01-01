package ca.ulaval.glo4003.spamdul.parking.usecases.campusaccess.dto;

import ca.ulaval.glo4003.spamdul.parking.entities.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;

public class CampusAccessDto {

  public UserId userId;
  public TimePeriodDto timePeriodDto;
  public CampusAccessCode code;
}
