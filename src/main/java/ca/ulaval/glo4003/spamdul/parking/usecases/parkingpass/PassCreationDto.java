package ca.ulaval.glo4003.spamdul.parking.usecases.parkingpass;

import ca.ulaval.glo4003.spamdul.parking.entities.parkinguser.UserId;
import ca.ulaval.glo4003.spamdul.parking.entities.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.time.entities.timeperiod.TimePeriodDto;

public class PassCreationDto {

  public DeliveryDto deliveryDto;
  public ParkingZone parkingZone;
  public TimePeriodDto timePeriodDto;
  public UserId userId;
}
