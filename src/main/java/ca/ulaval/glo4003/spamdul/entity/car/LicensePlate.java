package ca.ulaval.glo4003.spamdul.entity.car;

import java.util.Objects;

public class LicensePlate {

  private String licensePlateNumber;

  public LicensePlate(String licensePlateNumber) {
    this.licensePlateNumber = licensePlateNumber;
  }

  public String toString() {
    return licensePlateNumber;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LicensePlate that = (LicensePlate) o;
    return Objects.equals(licensePlateNumber, that.licensePlateNumber);
  }

  public int hashCode() {
    return Objects.hash(licensePlateNumber);
  }
}
