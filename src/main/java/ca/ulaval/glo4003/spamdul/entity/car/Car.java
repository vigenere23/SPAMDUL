package ca.ulaval.glo4003.spamdul.entity.car;

public class Car {

  private final CarId carId;
  private final CarType carType;
  private final String brand;
  private final String model;
  private final int year;
  private final String licencePlate;

  public Car(CarId carId, CarType carType, String brand, String model, int year, String licencePlate) {
    this.carId = carId;
    this.carType = carType;
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.licencePlate = licencePlate;
  }

  public CarId getCarId() {
    return carId;
  }

  public CarType getCarType() {
    return carType;
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

  public String getLicencePlate() {
    return licencePlate;
  }
}
