package ca.ulaval.glo4003.spamdul.parking2.usecases.dtos;

import ca.ulaval.glo4003.spamdul.parking2.entities.car.CarType;
import ca.ulaval.glo4003.spamdul.parking2.entities.car.LicensePlate;

public class CarDto {

  public String brand;
  public String model;
  public int year;
  public LicensePlate licensePlate;
  public CarType type;
}
