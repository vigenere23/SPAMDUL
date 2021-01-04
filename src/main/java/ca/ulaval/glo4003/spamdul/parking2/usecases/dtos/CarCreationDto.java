package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;
import ca.ulaval.glo4003.spamdul.parking2.entities.user.ParkingUserId;

public class CarCreationDto {

  public ParkingUserId userId;
  public String brand;
  public String model;
  public int year;
  public CarType carType;
  public LicensePlate licensePlate;
}
