package ca.ulaval.glo4003.spamdul.usecases.sale;

import ca.ulaval.glo4003.spamdul.entity.pass.ParkingZone;
import ca.ulaval.glo4003.spamdul.entity.pass.PassType;
import ca.ulaval.glo4003.spamdul.entity.user.UserId;

public class PassSaleDto {
  public UserId userId;
  public DeliveryDto deliveryDto;
  public PassType passType;
  public ParkingZone parkingZone;
}
