package ca.ulaval.glo4003.spamdul.parking2.entities.car;

public class Car {

  private final String brand;
  private final String model;
  private final int year;
  private final CarType carType;
  private final LicensePlate licensePlate;

  public Car(String brand, String model, int year, LicensePlate licensePlate,
             CarType carType) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.licensePlate = licensePlate;
    this.carType = carType;
  }

  public LicensePlate getLicensePlate() {
    return licensePlate;
  }

  public String getBrand() {
    return brand;
  }

  public String getModel() {
    return model;
  }

  public int getYear() {
    return year;
  }

  public CarType getType() {
    return carType;
  }
}
