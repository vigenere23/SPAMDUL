package ca.ulaval.glo4003.spamdul.usecases.parking.pass;

import ca.ulaval.glo4003.spamdul.entity.parking.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.timeperiod.TimePeriodDto;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class PassDto {

  public DeliveryDto deliveryDto;
  public ParkingZone parkingZone;
  public TimePeriodDto timePeriodDto;
  public UserId userId;
}
