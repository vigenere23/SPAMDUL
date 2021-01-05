package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.permit.PermitType;

public class PermitCreationDto {

  public PermitType type;
  public String carBrand;
  public String carModel;
  public int carYear;
  public CarType carType;
  public LicensePlate licensePlate;
  public PermitDeliveryDto delivery;
}
