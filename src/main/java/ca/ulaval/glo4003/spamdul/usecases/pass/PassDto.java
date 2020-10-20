package ca.ulaval.glo4003.spamdul.usecases.pass;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;

public class PassDto {

  public DeliveryDto deliveryDto;
  public ParkingZone parkingZone;
  public TimePeriodDto timePeriodDto;
  public CampusAccessCode campusAccessCode;
}
