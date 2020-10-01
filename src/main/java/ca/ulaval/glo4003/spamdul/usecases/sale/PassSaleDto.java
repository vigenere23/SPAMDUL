package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.campusaccess.CampusAccessCode;
import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

import java.time.DayOfWeek;

public class PassSaleDto {
  public DeliveryDto deliveryDto;
  public PassType passType;
  public ParkingZone parkingZone;
  public DayOfWeek dayOfWeek;
  public CampusAccessCode campusAccessCode;
}
