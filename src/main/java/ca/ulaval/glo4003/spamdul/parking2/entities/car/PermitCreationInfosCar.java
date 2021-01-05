package ca.ulaval.glo4003.spamdul.parking2.entities.car;

public class PermitCreationInfosCar {

  private final String brand;
  private final String model;
  private final int year;
  private final LicensePlate licensePlate;
  private final CarType type;

  public PermitCreationInfosCar(String brand,
                                String model,
                                int year,
                                LicensePlate licensePlate, CarType type) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.licensePlate = licensePlate;
    this.type = type;
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
    return type;
  }

  public LicensePlate getLicensePlate() {
    return licensePlate;
  }
}
